package kh.com.metfone.emoney.eshop.ui.forgotpassword;

import kh.com.metfone.emoney.eshop.R;
import kh.com.metfone.emoney.eshop.consts.Const;
import kh.com.metfone.emoney.eshop.data.domain.ClientLogUseCase;
import kh.com.metfone.emoney.eshop.data.domain.ForgotPassChangeUseCase;
import kh.com.metfone.emoney.eshop.data.domain.GetCodeForgotPassUseCase;
import kh.com.metfone.emoney.eshop.data.models.BaseResult;
import kh.com.metfone.emoney.eshop.data.models.ClientLog;
import kh.com.metfone.emoney.eshop.ui.base.BaseSaveLogPresenter;
import kh.com.metfone.emoney.eshop.utils.DateUtils;

import java.net.SocketTimeoutException;
import java.util.Calendar;

import javax.inject.Inject;
import javax.net.ssl.SSLException;

import io.reactivex.subscribers.DisposableSubscriber;
import retrofit2.HttpException;

/**
 * Created by DCV on 3/5/2018.
 */

public class ForgotPasswordPresenter extends BaseSaveLogPresenter<ForgotPasswordMvpView> {
    private final GetCodeForgotPassUseCase getCodeForgotPassUseCase;
    private final ForgotPassChangeUseCase forgotPassChangeUseCase;

    @Inject
    ForgotPasswordPresenter(GetCodeForgotPassUseCase getCodeForgotPassUseCase,
                            ForgotPassChangeUseCase forgotPassChangeUseCase,
                            ClientLogUseCase clientLogUseCase) {
        super(clientLogUseCase);
        this.getCodeForgotPassUseCase = getCodeForgotPassUseCase;
        this.forgotPassChangeUseCase = forgotPassChangeUseCase;
    }

    public void getCodeForForgotPassword(String phoneNumber) {
        if(getMvpView() != null) {
            getMvpView().showProgressDialog(true);
        }
        long startTime = Calendar.getInstance().getTimeInMillis();
        ClientLog clientLog = new ClientLog(DateUtils.formatDateTime(Calendar.getInstance().getTime(), DateUtils.PATTERN_DD_MM_YYYY_HH_MM_SS),
                Calendar.getInstance().getTimeInMillis(),
                "",
                "",
                "auth/forgotpass/getcode",
                ForgotPasswordPresenter.class.getName(),
                "getCodeForForgotPassword");
        getCodeForgotPassUseCase.execute(new DisposableSubscriber<BaseResult>() {
            @Override
            public void onNext(BaseResult baseResult) {
                long endTime = Calendar.getInstance().getTimeInMillis();
                clientLog.setEndTime(DateUtils.formatDateTime(Calendar.getInstance().getTime(), DateUtils.PATTERN_DD_MM_YYYY_HH_MM_SS));
                clientLog.setDuration(endTime - startTime);

                if (getMvpView() != null) {
                    getMvpView().showProgressDialog(false);
                    if (baseResult.getStatus() != Const.VALUE_SUCCESS_CODE) {
                        getMvpView().notifyError(baseResult.getMessage());
                    } else {
                        getMvpView().notifySuccess(baseResult.getMessage());
                    }
                }

                clientLog.setStatus(baseResult.getStatus());
                String requestContent = new StringBuilder(Const.KEY_MSISDN)
                        .append(":")
                        .append(phoneNumber)
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

                if(getMvpView() != null) {
                    getMvpView().showProgressDialog(false);
                    if (t instanceof SocketTimeoutException || t instanceof SSLException) {
                        getMvpView().notifyError(R.string.connect_timeout);
                    } else {
                        getMvpView().notifySuccess(t.getMessage());
                    }
                }
                onComplete();
            }

            @Override
            public void onComplete() {
                saveLog(clientLog);
            }
        }, GetCodeForgotPassUseCase.Param.forParam(phoneNumber));
    }

    public void changePassword(String phoneNumber, String privateKey, String password) {
        getMvpView().showProgressDialog(true);

        long startTime = Calendar.getInstance().getTimeInMillis();
        ClientLog clientLog = new ClientLog(DateUtils.formatDateTime(Calendar.getInstance().getTime(), DateUtils.PATTERN_DD_MM_YYYY_HH_MM_SS),
                Calendar.getInstance().getTimeInMillis(),
                "",
                "",
                "auth/forgot-password",
                ForgotPasswordView.class.getName(),
                "changePassword");
        forgotPassChangeUseCase.execute(new DisposableSubscriber<BaseResult>() {
            @Override
            public void onNext(BaseResult baseResult) {
                long endTime = Calendar.getInstance().getTimeInMillis();
                clientLog.setEndTime(DateUtils.formatDateTime(Calendar.getInstance().getTime(), DateUtils.PATTERN_DD_MM_YYYY_HH_MM_SS));
                clientLog.setDuration(endTime - startTime);


                if (getMvpView() != null) {
                    getMvpView().showProgressDialog(false);
                    if (baseResult.getStatus() == Const.VALUE_SUCCESS_CODE) {
                        getMvpView().changePasswordSuccess(baseResult.getMessage());
                    } else {
                        getMvpView().notifyError(baseResult.getMessage());
                    }
                }
                clientLog.setStatus(baseResult.getStatus());
                String requestContent = new StringBuilder(Const.KEY_MSISDN)
                        .append(":")
                        .append(phoneNumber)
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
                if (getMvpView() != null) {
                    getMvpView().showProgressDialog(false);
                    if (t instanceof SocketTimeoutException || t instanceof SSLException) {
                        getMvpView().notifyError(R.string.connect_timeout);
                    } else {
                        getMvpView().notifyError(t.getMessage());
                    }
                }
                onComplete();
            }

            @Override
            public void onComplete() {
                saveLog(clientLog);
            }
        }, ForgotPassChangeUseCase.Params.forParams(phoneNumber, privateKey, password));
    }

    @Override
    public void detachView() {
        forgotPassChangeUseCase.dispose();
        getCodeForgotPassUseCase.dispose();
        super.detachView();
    }
}
