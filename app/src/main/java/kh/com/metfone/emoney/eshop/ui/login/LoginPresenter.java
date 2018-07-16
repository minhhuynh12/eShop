package kh.com.metfone.emoney.eshop.ui.login;

import kh.com.metfone.emoney.eshop.R;
import kh.com.metfone.emoney.eshop.consts.Const;
import kh.com.metfone.emoney.eshop.data.domain.ClientLogUseCase;
import kh.com.metfone.emoney.eshop.data.domain.LoginUseCase;
import kh.com.metfone.emoney.eshop.data.models.ClientLog;
import kh.com.metfone.emoney.eshop.data.models.UserInformation;
import kh.com.metfone.emoney.eshop.ui.base.BaseSaveLogPresenter;
import kh.com.metfone.emoney.eshop.utils.DataUtils;
import kh.com.metfone.emoney.eshop.utils.DateUtils;
import kh.com.metfone.emoney.eshop.utils.SharePreferenceHelper;

import java.net.SocketTimeoutException;
import java.util.Calendar;

import javax.inject.Inject;
import javax.net.ssl.SSLException;

import io.reactivex.subscribers.DisposableSubscriber;
import retrofit2.HttpException;
import retrofit2.Retrofit;

/**
 * Created by DCV on 3/1/2018.
 */

public class LoginPresenter extends BaseSaveLogPresenter<LoginMvpView> {

    private final LoginUseCase loginUseCase;
    private final SharePreferenceHelper sharePreferenceHelper;
    private final Retrofit retrofit;

    @Inject
    LoginPresenter(LoginUseCase loginUseCase,
                   SharePreferenceHelper sharePreferenceHelper,
                   ClientLogUseCase clientLogUseCase,
                   Retrofit retrofit) {
        super(clientLogUseCase);
        this.loginUseCase = loginUseCase;
        this.sharePreferenceHelper = sharePreferenceHelper;
        this.retrofit = retrofit;
    }

    public void login(final String userName, String password, String fireBaseToken) {
        if (getMvpView() != null) {
            getMvpView().showProgressDialog(true);
        }
        long startTime = Calendar.getInstance().getTimeInMillis();
        ClientLog clientLog = new ClientLog(DateUtils.formatDateTime(Calendar.getInstance().getTime(), DateUtils.PATTERN_DD_MM_YYYY_HH_MM_SS),
                Calendar.getInstance().getTimeInMillis(),
                "",
                "",
                "auth/login",
                LoginPresenter.class.getName(),
                "login");
        loginUseCase.execute(new DisposableSubscriber<UserInformation>() {
            @Override
            public void onNext(UserInformation userInformation) {
                long endTime = Calendar.getInstance().getTimeInMillis();
                clientLog.setEndTime(DateUtils.formatDateTime(Calendar.getInstance().getTime(), DateUtils.PATTERN_DD_MM_YYYY_HH_MM_SS));
                clientLog.setDuration(endTime - startTime);

                DataUtils.setUserInformation(userInformation);
                if (userInformation.getStatus() == Const.VALUE_SUCCESS_CODE) {
                    clientLog.setStaffCode(userInformation.getUserInfo().getStaffCode());
                    clientLog.setLoggedToken(userInformation.getUserInfo().getAccessToken());
                    userInformation.setId(userInformation.getUserInfo().getStaffId());
                    loginUseCase.saveUserInfo(userInformation);
                    sharePreferenceHelper.putAccessToken(userInformation.getUserInfo().getAccessToken());
                    sharePreferenceHelper.putUserName(userInformation.getUserInfo().getStaffCode());
                    if (getMvpView() != null) {
                        getMvpView().loginSuccess(userInformation);
                        getMvpView().showProgressDialog(false);
                    }
                } else {
                    if (getMvpView() != null) {
                        getMvpView().loginFailed(userInformation.getMessage());
                        getMvpView().showProgressDialog(false);
                    }
                }

                clientLog.setStatus(userInformation.getStatus());
                String requestContent = new StringBuilder(Const.KEY_USERNAME)
                        .append(":")
                        .append(userName)
                        .toString();
                clientLog.setRequestContent(requestContent);
                String responseContent = new StringBuilder(String.valueOf(userInformation.getStatus()))
                        .append("|")
                        .append(userInformation.getCode())
                        .append("|")
                        .append(userInformation.getMessage())
                        .toString();
                clientLog.setResponseContent(responseContent);
                clientLog.setErrorCode(userInformation.getStatus());

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
                    if (t instanceof SocketTimeoutException || t instanceof SSLException) {
                        getMvpView().notifyError(R.string.connect_timeout);
                    } else {
                        getMvpView().loginFailed(t.getMessage());
                    }
                }
                onComplete();
            }

            @Override
            public void onComplete() {
                saveLog(clientLog);

            }
        }, LoginUseCase.Params.forParams(userName, password, fireBaseToken));
    }

    @Override
    public void detachView() {
        loginUseCase.dispose();
        super.detachView();
    }
}
