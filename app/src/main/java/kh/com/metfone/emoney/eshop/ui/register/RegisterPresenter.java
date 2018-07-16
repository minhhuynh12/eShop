package kh.com.metfone.emoney.eshop.ui.register;

import android.util.Log;

import kh.com.metfone.emoney.eshop.R;
import kh.com.metfone.emoney.eshop.consts.Const;
import kh.com.metfone.emoney.eshop.data.domain.ClientLogUseCase;
import kh.com.metfone.emoney.eshop.data.domain.GetCodeRegisterUseCase;
import kh.com.metfone.emoney.eshop.data.domain.RegisterUseCase;
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
 * Created by DCV on 3/1/2018.
 */

public class RegisterPresenter extends BaseSaveLogPresenter<RegisterMvpView> {
    private final GetCodeRegisterUseCase getCodeRegisterUseCase;
    private final RegisterUseCase registerUseCase;

    @Inject
    RegisterPresenter(GetCodeRegisterUseCase getCodeRegisterUseCase,
                      RegisterUseCase usecase,
                      ClientLogUseCase clientLogUseCase) {
        super(clientLogUseCase);
        this.getCodeRegisterUseCase = getCodeRegisterUseCase;
        this.registerUseCase = usecase;

    }

    public void getCodeForRegister(String phoneNumber) {
        if (getMvpView() != null) {
            getMvpView().showProgressDialog(true);
        }
        long startTime = Calendar.getInstance().getTimeInMillis();
        ClientLog clientLog = new ClientLog(DateUtils.formatDateTime(Calendar.getInstance().getTime(), DateUtils.PATTERN_DD_MM_YYYY_HH_MM_SS),
                Calendar.getInstance().getTimeInMillis(),
                "",
                "",
                "/auth/register/getcode",
                RegisterPresenter.class.getName(),
                "getCodeForRegister");
        getCodeRegisterUseCase.execute(new DisposableSubscriber<BaseResult>() {
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
        }, GetCodeRegisterUseCase.Param.forParam(phoneNumber));
    }


    public void register(String username, String private_Code, String password, String category_id, String shop_name, String address) {
        if (getMvpView() != null) {
            getMvpView().showProgressDialog(true);
        }
        long startTime = Calendar.getInstance().getTimeInMillis();
        ClientLog clientLog = new ClientLog(DateUtils.formatDateTime(Calendar.getInstance().getTime(), DateUtils.PATTERN_DD_MM_YYYY_HH_MM_SS),
                Calendar.getInstance().getTimeInMillis(),
                "",
                "",
                "/auth/register",
                RegisterPresenter.class.getName(),
                "register");
        registerUseCase.execute(new DisposableSubscriber<BaseResult>() {
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
                        getMvpView().onSuccessRegister(baseResult.getMessage());
                    }
                }


                clientLog.setStatus(baseResult.getStatus());
                String requestContent = new StringBuilder(Const.KEY_MSISDN)
                        .append(":")
                        .append(username)
                        .append("|")
                        .append(Const.KEY_SHOP_TYPE_ID)
                        .append(":")
                        .append(category_id)
                        .append("|")
                        .append(Const.KEY_SHOP_NAME)
                        .append(":")
                        .append(shop_name)
                        .append("|")
                        .append(Const.KEY_SHOP_ADDRESS)
                        .append(":")
                        .append(address)
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
                Log.d("", "");
                saveLog(clientLog);
            }
        }, RegisterUseCase.Param.forParam(username, private_Code, password, category_id, shop_name, address));
    }

    @Override
    public void detachView() {
        getCodeRegisterUseCase.dispose();
        super.detachView();
    }
}
