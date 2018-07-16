package kh.com.metfone.emoney.eshop.ui.setting;

import kh.com.metfone.emoney.eshop.R;
import kh.com.metfone.emoney.eshop.consts.Const;
import kh.com.metfone.emoney.eshop.data.domain.ClientLogUseCase;
import kh.com.metfone.emoney.eshop.data.domain.GetUserInforUseCase;
import kh.com.metfone.emoney.eshop.data.domain.LogoutUseCase;
import kh.com.metfone.emoney.eshop.data.domain.SetChangeExchangeRateUseCase;
import kh.com.metfone.emoney.eshop.data.event.ChangeExchangeRateSuccess;
import kh.com.metfone.emoney.eshop.data.models.BaseResult;
import kh.com.metfone.emoney.eshop.data.models.ClientLog;
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
import io.realm.RealmList;
import retrofit2.HttpException;

/**
 * Created by DCV on 3/16/2018.
 */

public class ChangeExchangeRatePresenter extends BaseSaveLogCheckAuthenPresenter<ChangeExchangeRateMvpView> {
    private final SetChangeExchangeRateUseCase setChangeExchangeRateUseCase;
    private final SharePreferenceHelper sharePreferenceHelper;
    private final GetUserInforUseCase getUserInforUseCase;

    @Inject
    ChangeExchangeRatePresenter(SetChangeExchangeRateUseCase setChangeExchangeRateUseCase,
                                ClientLogUseCase clientLogUseCase,
                                LogoutUseCase logoutUseCase,
                                SharePreferenceHelper sharePreferenceHelper,
                                GetUserInforUseCase getUserInforUseCase) {
        super(clientLogUseCase, logoutUseCase);
        this.setChangeExchangeRateUseCase = setChangeExchangeRateUseCase;
        this.sharePreferenceHelper = sharePreferenceHelper;
        this.getUserInforUseCase = getUserInforUseCase;
    }

    /**
     * call api to change exchange rate, save log into reaml and server after get response
     *
     * @param staffCode: staff code
     * @param khrMoney:  1 USD = khrMoney
     */
    public void changeExchangeRate(String staffCode, String khrMoney) {
        long startTime = Calendar.getInstance().getTimeInMillis();
        ClientLog clientLog = new ClientLog(DateUtils.formatDateTime(Calendar.getInstance().getTime(), DateUtils.PATTERN_DD_MM_YYYY_HH_MM_SS),
                Calendar.getInstance().getTimeInMillis(),
                sharePreferenceHelper.getAccessToken(),
                staffCode,
                "setting",
                ChangeExchangeRatePresenter.class.getName(),
                "changeExchangeRate");
        setChangeExchangeRateUseCase.execute(new DisposableSubscriber<BaseResult>() {
            @Override
            public void onNext(BaseResult baseResult) {
                long endTime = Calendar.getInstance().getTimeInMillis();
                clientLog.setEndTime(DateUtils.formatDateTime(Calendar.getInstance().getTime(), DateUtils.PATTERN_DD_MM_YYYY_HH_MM_SS));
                clientLog.setDuration(endTime - startTime);


                if (getMvpView() != null) {
                    if (Const.VALUE_SUCCESS_CODE == baseResult.getStatus()) {
                        getMvpView().changeExchangeRateSuccess(new ChangeExchangeRateSuccess(khrMoney, baseResult.getMessage()));
                        saveConfig(khrMoney);
                    } else {
                        getMvpView().changeExchangeRateFailed(baseResult.getMessage());
                    }
                }


                clientLog.setStatus(baseResult.getStatus());
                String requestContent = new StringBuilder(Const.KEY_STTYPE)
                        .append(":")
                        .append("1")
                        .append("|")
                        .append(Const.KEY_STKEY)
                        .append(":")
                        .append(Const.CURRENCY_EXCHANGE_RATE_USD_KHR)
                        .append("|")
                        .append(Const.KEY_STVALUE)
                        .append(":")
                        .append(khrMoney)
                        .toString();
                clientLog.setRequestContent(requestContent);
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
                // show failed error
                if (getMvpView() != null) {
                    if (t instanceof HttpException && Const.VALUE_UNAUTHORIZED_ERROR_CODE == ((HttpException) t).code()) {
                        logoutUseCase.clearUserInfor();
                        clientLogUseCase.clearClientLogLocal();
                        getMvpView().httpUnauthorizedError();
                    } else {
                        if (t instanceof SocketTimeoutException || t instanceof SSLException) {
                            getMvpView().notifyError(R.string.connect_timeout);
                        } else {
                            getMvpView().changeExchangeRateFailed(t.getMessage());
                        }
                    }
                }
                onComplete();
            }

            @Override
            public void onComplete() {
                saveLog(clientLog);
            }
        }, SetChangeExchangeRateUseCase.Param.forParam(khrMoney));
    }

    private void saveConfig(String configValue) {
        getUserInforUseCase.execute(new DisposableSubscriber<UserInformation>() {
            @Override
            public void onNext(UserInformation userInformation) {
                if (userInformation.getShopConfigs() != null) {
                    if (userInformation.getShopConfigs().size() > 0) {
                        userInformation.getShopConfigs().get(0).setConfigValue(configValue);
                    } else {
                        ShopConfig shopConfig = new ShopConfig();
                        shopConfig.setConfigValue(configValue);
                        userInformation.getShopConfigs().add(shopConfig);
                    }

                } else {
                    userInformation.setShopConfigs(new RealmList<ShopConfig>());
                    ShopConfig shopConfig = new ShopConfig();
                    shopConfig.setConfigValue(configValue);
                    userInformation.getShopConfigs().add(shopConfig);
                }
                getUserInforUseCase.saveUserInfo(userInformation);
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
        setChangeExchangeRateUseCase.dispose();
        super.detachView();
    }
}
