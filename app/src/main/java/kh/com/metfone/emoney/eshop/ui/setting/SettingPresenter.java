package kh.com.metfone.emoney.eshop.ui.setting;

import android.bluetooth.BluetoothDevice;

import kh.com.metfone.emoney.eshop.R;
import kh.com.metfone.emoney.eshop.consts.Const;
import kh.com.metfone.emoney.eshop.data.domain.ClientLogUseCase;
import kh.com.metfone.emoney.eshop.data.domain.GetLocalCommonConfigUseCase;
import kh.com.metfone.emoney.eshop.data.domain.GetShopConfigUseCase;
import kh.com.metfone.emoney.eshop.data.domain.GetUserInforUseCase;
import kh.com.metfone.emoney.eshop.data.domain.LogoutUseCase;
import kh.com.metfone.emoney.eshop.data.models.BaseResult;
import kh.com.metfone.emoney.eshop.data.models.ClientLog;
import kh.com.metfone.emoney.eshop.data.models.CommonConfigInfo;
import kh.com.metfone.emoney.eshop.data.models.ShopConfig;
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
 * Created by DCV on 3/5/2018.
 */

public class SettingPresenter extends BaseSaveLogCheckAuthenPresenter<SettingMvpView> {

    private final SharePreferenceHelper sharePreferenceHelper;
    private final GetLocalCommonConfigUseCase getLocalCommonConfigUseCase;
    private final GetShopConfigUseCase getShopConfigUseCase;
    private final GetUserInforUseCase getUserInforUseCase;


    @Inject
    SettingPresenter(LogoutUseCase logoutUseCase, ClientLogUseCase clientLogUseCase,
                     SharePreferenceHelper sharePreferenceHelper,
                     GetLocalCommonConfigUseCase getLocalCommonConfigUseCase,
                     GetShopConfigUseCase getShopConfigUseCase,
                     GetUserInforUseCase getUserInforUseCase) {
        super(clientLogUseCase, logoutUseCase);
        this.sharePreferenceHelper = sharePreferenceHelper;
        this.getLocalCommonConfigUseCase = getLocalCommonConfigUseCase;
        this.getShopConfigUseCase = getShopConfigUseCase;
        this.getUserInforUseCase = getUserInforUseCase;
        getShopConfig();
    }

    @Override
    public void attachView(SettingMvpView mvpView) {
        super.attachView(mvpView);
        getPrinterName();
        getUserInfo();
    }

    private void getUserInfo() {
        getUserInforUseCase.execute(new DisposableSubscriber<UserInformation>() {
            @Override
            public void onNext(UserInformation userInformation) {
                if (getMvpView() != null) {
                    getMvpView().getUserInfoSuccess(userInformation);
                }
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        }, null);
    }

    protected void logout(String staffCode) {
        if (getMvpView() != null) {
            getMvpView().showProgressDialog(true);
        }
        long startTime = Calendar.getInstance().getTimeInMillis();
        ClientLog clientLog = new ClientLog(DateUtils.formatDateTime(Calendar.getInstance().getTime(), DateUtils.PATTERN_DD_MM_YYYY_HH_MM_SS),
                Calendar.getInstance().getTimeInMillis(),
                sharePreferenceHelper.getAccessToken(),
                staffCode,
                "auth/logout",
                SettingPresenter.class.getName(),
                "logout");
        logoutUseCase.execute(new DisposableSubscriber<BaseResult>() {
            @Override
            public void onNext(BaseResult baseResult) {
                long endTime = Calendar.getInstance().getTimeInMillis();
                clientLog.setEndTime(DateUtils.formatDateTime(Calendar.getInstance().getTime(), DateUtils.PATTERN_DD_MM_YYYY_HH_MM_SS));
                clientLog.setDuration(endTime - startTime);
                clientLogUseCase.clearClientLogLocal();
                if (getMvpView() != null) {
                    getMvpView().showProgressDialog(false);
                    if (Const.VALUE_SUCCESS_CODE == baseResult.getStatus()) {
                        logoutUseCase.clearUserInfor();

                        getMvpView().logoutSuccess();
                    } else {
                        getMvpView().logoutFailed(baseResult.getMessage());
                    }
                }


                clientLog.setStatus(baseResult.getStatus());
                String responseContent = new StringBuilder(String.valueOf(baseResult.getStatus()))
                        .append("|")
                        .append(baseResult.getCode())
                        .append("|")
                        .append(baseResult.getMessage())
                        .toString();
                clientLog.setResponseContent(responseContent);
                clientLog.setErrorCode(baseResult.getStatus());
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
                            getMvpView().logoutFailed(t.getMessage());
                        }
                    }
                }
                onComplete();
            }

            @Override
            public void onComplete() {
                saveLog(clientLog);
            }
        }, null);
    }

    @Override
    public void detachView() {
        logoutUseCase.dispose();
        super.detachView();
    }

    public void savePrinterAddress(BluetoothDevice bluetoothDevice) {
        sharePreferenceHelper.putPrinterName(bluetoothDevice.getName());
        sharePreferenceHelper.putPrinterAddress(bluetoothDevice.getAddress());
    }

    public void getPrinterName() {
        if (getMvpView() != null) {
            getMvpView().getPrinterNameSuccess(sharePreferenceHelper.getPrinterName());
        }
    }

    protected void getShopConfig() {
        getShopConfigUseCase.execute(new DisposableSubscriber<ShopConfig>() {
            @Override
            public void onNext(ShopConfig shopConfig) {
                if (getMvpView() != null) {
                    getMvpView().getConfigSuccess(shopConfig);
                }
            }

            @Override
            public void onError(Throwable t) {
                getCommonConfig();

            }

            @Override
            public void onComplete() {

            }
        }, null);
    }

    protected void getCommonConfig() {
        getLocalCommonConfigUseCase.execute(new DisposableSubscriber<CommonConfigInfo>() {
            @Override
            public void onNext(CommonConfigInfo commonConfigInfo) {
                if (getMvpView() != null) {
                    getMvpView().getConfigSuccess(commonConfigInfo.getAppConfigInfo().get(0));
                }
            }

            @Override
            public void onError(Throwable t) {
                if (getMvpView() != null) {
                    getMvpView().getConfigFailed(t.getMessage());
                }

            }

            @Override
            public void onComplete() {

            }
        }, null);
    }
}
