package kh.com.metfone.emoney.eshop.ui.shopupdate;

import kh.com.metfone.emoney.eshop.R;
import kh.com.metfone.emoney.eshop.consts.Const;
import kh.com.metfone.emoney.eshop.data.domain.ClientLogUseCase;
import kh.com.metfone.emoney.eshop.data.domain.GetUserInforUseCase;
import kh.com.metfone.emoney.eshop.data.domain.LogoutUseCase;
import kh.com.metfone.emoney.eshop.data.domain.UpdateShopInfoUseCase;
import kh.com.metfone.emoney.eshop.data.models.ClientLog;
import kh.com.metfone.emoney.eshop.data.models.UpdateShopInfo;
import kh.com.metfone.emoney.eshop.data.models.UserInformation;
import kh.com.metfone.emoney.eshop.ui.base.BaseSaveLogCheckAuthenPresenter;
import kh.com.metfone.emoney.eshop.utils.DateUtils;
import kh.com.metfone.emoney.eshop.utils.SharePreferenceHelper;

import java.net.SocketTimeoutException;
import java.util.Calendar;

import javax.inject.Inject;
import javax.net.ssl.SSLException;

import io.reactivex.subscribers.DisposableSubscriber;
import retrofit2.HttpException;

/**
 * Created by Administrator on 3/7/2018.
 */

public class ShopUpdatePresenter extends BaseSaveLogCheckAuthenPresenter<ShopUpdateMvpView> {
    private final UpdateShopInfoUseCase updateShopInfoUseCase;
    private final SharePreferenceHelper sharePreferenceHelper;
    private final GetUserInforUseCase getUserInforUseCase;

    @Inject
    ShopUpdatePresenter(UpdateShopInfoUseCase updateShopInfoUseCase,
                        ClientLogUseCase clientLogUseCase, LogoutUseCase logoutUseCase,
                        SharePreferenceHelper sharePreferenceHelper,
                        GetUserInforUseCase getUserInforUseCase) {
        super(clientLogUseCase, logoutUseCase);
        this.updateShopInfoUseCase = updateShopInfoUseCase;
        this.sharePreferenceHelper = sharePreferenceHelper;
        this.getUserInforUseCase = getUserInforUseCase;
    }

    public void updateShopInfo(String shopName, String contactPhone, String contactEmail, String areaCode, String shopAddress) {
        if (getMvpView() != null) {
            getMvpView().showProgressDialog(true);
        }
        long startTime = Calendar.getInstance().getTimeInMillis();
        ClientLog clientLog = new ClientLog(DateUtils.formatDateTime(Calendar.getInstance().getTime(), DateUtils.PATTERN_DD_MM_YYYY_HH_MM_SS),
                Calendar.getInstance().getTimeInMillis(),
                sharePreferenceHelper.getAccessToken(),
                sharePreferenceHelper.getUserName(),
                "shop/update",
                ShopUpdatePresenter.class.getName(),
                "updateShopInfo");
        updateShopInfoUseCase.execute(new DisposableSubscriber<UpdateShopInfo>() {
            @Override
            public void onNext(UpdateShopInfo updateShopInfo) {
                long endTime = Calendar.getInstance().getTimeInMillis();
                clientLog.setEndTime(DateUtils.formatDateTime(Calendar.getInstance().getTime(), DateUtils.PATTERN_DD_MM_YYYY_HH_MM_SS));
                clientLog.setDuration(endTime - startTime);
                if (getMvpView() != null) {
                    getMvpView().showProgressDialog(false);
                    if (Const.VALUE_SUCCESS_CODE == updateShopInfo.getStatus()) {
                        saveUserInfor(updateShopInfo);
                    } else {
                        getMvpView().updateShopInfoFailed(updateShopInfo.getMessage());
                    }
                }

                String requestContent = new StringBuilder(Const.KEY_SHOP_NAME)
                        .append(":")
                        .append(shopName)
                        .append("|")
                        .append(Const.KEY_CONTACT_PHONE)
                        .append(":")
                        .append(contactPhone)
                        .append("|")
                        .append(Const.KEY_CONTACT_EMAIL)
                        .append(":")
                        .append(contactEmail)
                        .append("|")
                        .append(Const.KEY_AREA_CODE)
                        .append(":")
                        .append(areaCode)
                        .append("|")
                        .append(Const.KEY_SHOP_ADDRESS)
                        .append(":")
                        .append(shopAddress)
                        .toString();
                clientLog.setRequestContent(requestContent);
                String responseContent = new StringBuilder(String.valueOf(updateShopInfo.getStatus()))
                        .append("|")
                        .append(updateShopInfo.getCode())
                        .append("|")
                        .append(updateShopInfo.getMessage())
                        .toString();
                clientLog.setResponseContent(responseContent);
                clientLog.setErrorCode(updateShopInfo.getStatus());
            }

            @Override
            public void onError(Throwable t) {
                long endTime = Calendar.getInstance().getTimeInMillis();
                clientLog.setEndTime(DateUtils.formatDateTime(Calendar.getInstance().getTime(), DateUtils.PATTERN_DD_MM_YYYY_HH_MM_SS));
                clientLog.setDuration(endTime - startTime);
                if (t instanceof HttpException) {
                    clientLog.setErrorCode(((HttpException) t).code());
                }
                if (getMvpView() != null) {
                    getMvpView().showProgressDialog(false);

                    if (t instanceof HttpException && Const.VALUE_UNAUTHORIZED_ERROR_CODE == ((HttpException) t).code()) {
                        logoutUseCase.clearUserInfor();
                        clientLogUseCase.clearClientLogLocal();
                        getMvpView().httpUnauthorizedError();
                    } else {
                        if (t instanceof SocketTimeoutException || t instanceof SSLException) {
                            getMvpView().notifyError(R.string.connect_timeout);
                        } else {
                            getMvpView().updateShopInfoFailed(t.getMessage());
                        }
                    }
                }
                onComplete();
            }

            @Override
            public void onComplete() {
                saveLog(clientLog);
            }
        }, UpdateShopInfoUseCase.Params.forParams(shopName, contactPhone, contactEmail, areaCode, shopAddress));

    }

    private void saveUserInfor(UpdateShopInfo updateShopInfo) {
        getUserInforUseCase.execute(new DisposableSubscriber<UserInformation>() {
            @Override
            public void onNext(UserInformation userInformation) {
                userInformation.setShopInfor(updateShopInfo.getShopInfor());
                getUserInforUseCase.saveUserInfo(userInformation);
                getMvpView().updateShopInfoSuccess(updateShopInfo);
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        }, null);
    }

    @Override
    public void detachView() {
        if (updateShopInfoUseCase != null) {
            updateShopInfoUseCase.dispose();
        }
        super.detachView();
    }
}
