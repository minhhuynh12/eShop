package kh.com.metfone.emoney.eshop.ui.confirmphone;

import android.os.Handler;

import kh.com.metfone.emoney.eshop.R;
import kh.com.metfone.emoney.eshop.consts.Const;
import kh.com.metfone.emoney.eshop.data.domain.CheckStatusPaymentUseCase;
import kh.com.metfone.emoney.eshop.data.domain.ClientLogUseCase;
import kh.com.metfone.emoney.eshop.data.domain.GetUserInforUseCase;
import kh.com.metfone.emoney.eshop.data.domain.LogoutUseCase;
import kh.com.metfone.emoney.eshop.data.domain.USSDUseCase;
import kh.com.metfone.emoney.eshop.data.models.ClientLog;
import kh.com.metfone.emoney.eshop.data.models.Receipt;
import kh.com.metfone.emoney.eshop.data.models.UserInformation;
import kh.com.metfone.emoney.eshop.data.models.UssdResponse;
import kh.com.metfone.emoney.eshop.ui.base.BaseSaveLogCheckAuthenPresenter;
import kh.com.metfone.emoney.eshop.ui.generateqrcode.CheckStatusQRCodePresenter;
import kh.com.metfone.emoney.eshop.utils.DataUtils;
import kh.com.metfone.emoney.eshop.utils.DateUtils;
import kh.com.metfone.emoney.eshop.utils.SharePreferenceHelper;

import java.net.SocketTimeoutException;
import java.util.Calendar;

import javax.inject.Inject;
import javax.net.ssl.SSLException;

import io.reactivex.subscribers.DisposableSubscriber;
import retrofit2.HttpException;

/**
 * Created by DCV on 3/6/2018.
 */

public class CheckStatusUSSDPresenter extends BaseSaveLogCheckAuthenPresenter<CheckStatusUSSDMvpView> {
    private final CheckStatusPaymentUseCase checkStatusPaymentUseCase;
    private final SharePreferenceHelper sharePreferenceHelper;
    private final USSDUseCase ussdUseCase;
    private final GetUserInforUseCase getUserInforUseCase;

    @Inject
    CheckStatusUSSDPresenter(CheckStatusPaymentUseCase checkStatusUseCase,
                             ClientLogUseCase clientLogUseCase, USSDUseCase ussdUseCase,
                             LogoutUseCase logoutUseCase,
                             GetUserInforUseCase getUserInforUseCase,
                             SharePreferenceHelper sharePreferenceHelper) {
        super(clientLogUseCase, logoutUseCase);
        this.checkStatusPaymentUseCase = checkStatusUseCase;
        this.ussdUseCase = ussdUseCase;
        this.sharePreferenceHelper = sharePreferenceHelper;
        this.getUserInforUseCase = getUserInforUseCase;
    }

    @Override
    public void attachView(CheckStatusUSSDMvpView mvpView) {
        super.attachView(mvpView);
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


    protected void autoCheckStatus(Handler handler, String staffCode, int receiptId) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //request trang thai receipt
                checkStatus(staffCode, handler, receiptId, true);
                handler.postDelayed(this, 20000);
            }
        }, 0);

    }

    protected void checkStatus(String staffCode, Handler handler, int receiptId, boolean isAutoCheck) {
        if (!isAutoCheck) {
            getMvpView().showProgressDialog(true);
        }
        long startTime = Calendar.getInstance().getTimeInMillis();
        ClientLog clientLog = new ClientLog(DateUtils.formatDateTime(Calendar.getInstance().getTime(), DateUtils.PATTERN_DD_MM_YYYY_HH_MM_SS),
                Calendar.getInstance().getTimeInMillis(),
                sharePreferenceHelper.getAccessToken(),
                staffCode,
                "payment/receipt",
                CheckStatusQRCodePresenter.class.getName(),
                "checkStatus");
        checkStatusPaymentUseCase.execute(new DisposableSubscriber<Receipt>() {
            @Override
            public void onNext(Receipt receipt) {
                long endTime = Calendar.getInstance().getTimeInMillis();
                clientLog.setEndTime(DateUtils.formatDateTime(Calendar.getInstance().getTime(), DateUtils.PATTERN_DD_MM_YYYY_HH_MM_SS));
                clientLog.setDuration(endTime - startTime);

                if (getMvpView() != null) {
                    if (Const.VALUE_SUCCESS_CODE == receipt.getStatus()) {
                        switch (receipt.getReceiptInfo().getStatus()) {
                            case Const.VALUE_UNPAID:
                            case Const.VALUE_WAIT_PAID:
                                if (!isAutoCheck) {
                                    getMvpView().showProgressDialog(false);
                                    getMvpView().showPaidStatus(receipt.getMessage());
                                }
                                break;
                            case Const.VALUE_ALREADY_PAID:
                                handler.removeCallbacksAndMessages(null);
                                getMvpView().alreadyUSSDSuccess(receipt);
                                break;
                            case Const.VALUE_PAID_ERROR:
                                if (!isAutoCheck) {
                                    getMvpView().showProgressDialog(false);
                                }
                                handler.removeCallbacksAndMessages(null);
                                getMvpView().showPaidStatus(receipt.getMessage());
                                break;
                        }

                    } else {
                        if (!isAutoCheck) {
                            getMvpView().showProgressDialog(false);
                            getMvpView().showPaidStatus(receipt.getMessage());
                        }
                    }
                }

                clientLog.setStatus(receipt.getStatus());
                String requestContent = new StringBuilder("receiptId")
                        .append(":")
                        .append(receiptId)
                        .toString();
                clientLog.setRequestContent(requestContent);
                String responseContent = new StringBuilder(String.valueOf(receipt.getStatus()))
                        .append("|")
                        .append(receipt.getCode())
                        .append("|")
                        .append(receipt.getMessage())
                        .toString();
                clientLog.setResponseContent(responseContent);
                clientLog.setErrorCode(receipt.getStatus());

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
                    if (t instanceof HttpException && Const.VALUE_UNAUTHORIZED_ERROR_CODE == ((HttpException) t).code()) {
                        logoutUseCase.clearUserInfor();
                        clientLogUseCase.clearClientLogLocal();
                        getMvpView().httpUnauthorizedError();
                    } else {
                        if (!isAutoCheck) {
                            getMvpView().showProgressDialog(false);
                            if (t instanceof SocketTimeoutException || t instanceof SSLException) {
                                getMvpView().notifyError(R.string.connect_timeout);
                            } else {
                                getMvpView().showPaidStatus(t.getMessage());
                            }
                        }
                    }
                }
                onComplete();
            }

            @Override
            public void onComplete() {
                saveLog(clientLog);
            }
        }, CheckStatusPaymentUseCase.Param.forParam(receiptId));
    }


    public void confirmByPhone(String staffCode, Handler handler, int receiptId, boolean isAutoCheck) {
        if (getMvpView() != null) {
            getMvpView().showProgressDialog(true);
        }
        long startTime = Calendar.getInstance().getTimeInMillis();
        ClientLog clientLog = new ClientLog(DateUtils.formatDateTime(Calendar.getInstance().getTime(), DateUtils.PATTERN_DD_MM_YYYY_HH_MM_SS),
                Calendar.getInstance().getTimeInMillis(),
                sharePreferenceHelper.getAccessToken(),
                staffCode,
                "payment/ussd",
                CheckStatusUSSDPresenter.class.getName(),
                "confirmByPhone");
        ussdUseCase.execute(new DisposableSubscriber<UssdResponse>() {
            @Override
            public void onNext(UssdResponse receipt) {
                long endTime = Calendar.getInstance().getTimeInMillis();
                clientLog.setEndTime(DateUtils.formatDateTime(Calendar.getInstance().getTime(), DateUtils.PATTERN_DD_MM_YYYY_HH_MM_SS));
                clientLog.setDuration(endTime - startTime);
                if (getMvpView() != null) {
                    getMvpView().showProgressDialog(false);
                    if (Const.VALUE_SUCCESS_CODE == Integer.valueOf(receipt.getStatus())) {
                        autoCheckStatus(handler, DataUtils.getUserInformation().getUserInfo().getStaffCode(), receipt.getReceiptInfo().getTransReceiptId());
                    } else {
                            getMvpView().showPaidStatus(receipt.getMessage());
                    }
                }
                clientLog.setStatus(Integer.valueOf(receipt.getStatus()));
                String requestContent = new StringBuilder(Const.KEY_RECEIPT_ID)
                        .append(":")
                        .append(receiptId)
                        .toString();
                clientLog.setRequestContent(requestContent);
                String responseContent = new StringBuilder(String.valueOf(receipt.getStatus()))
                        .append("|")
                        .append(receipt.getCode())
                        .append("|")
                        .append(receipt.getMessage())
                        .toString();
                clientLog.setResponseContent(responseContent);
                clientLog.setErrorCode(Integer.valueOf(receipt.getStatus()));

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
                                getMvpView().showPaidStatus("Get paid status failed");
                            }
                    }
                }
                onComplete();
            }

            @Override
            public void onComplete() {
                saveLog(clientLog);
            }
        }, USSDUseCase.Param.forParam(receiptId));
    }
}
