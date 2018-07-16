package kh.com.metfone.emoney.eshop.ui.generateqrcode;

import android.bluetooth.BluetoothDevice;
import android.os.Handler;

import kh.com.metfone.emoney.eshop.R;
import kh.com.metfone.emoney.eshop.consts.Const;
import kh.com.metfone.emoney.eshop.data.domain.CheckStatusPaymentUseCase;
import kh.com.metfone.emoney.eshop.data.domain.ClientLogUseCase;
import kh.com.metfone.emoney.eshop.data.domain.GetUserInforUseCase;
import kh.com.metfone.emoney.eshop.data.domain.LogoutUseCase;
import kh.com.metfone.emoney.eshop.data.models.ClientLog;
import kh.com.metfone.emoney.eshop.data.models.Receipt;
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
 * Created by DCV on 3/6/2018.
 */

public class CheckStatusQRCodePresenter extends BaseSaveLogCheckAuthenPresenter<CheckStatusQRCodeMvpView> {
    private final CheckStatusPaymentUseCase checkStatusPaymentUseCase;
    private final SharePreferenceHelper sharePreferenceHelper;
    private final GetUserInforUseCase getUserInforUseCase;

    @Inject
    CheckStatusQRCodePresenter(CheckStatusPaymentUseCase checkStatusPaymentUseCase,
                               ClientLogUseCase clientLogUseCase,
                               LogoutUseCase logoutUseCase,
                               GetUserInforUseCase getUserInforUseCase,
                               SharePreferenceHelper sharePreferenceHelper) {
        super(clientLogUseCase, logoutUseCase);
        this.checkStatusPaymentUseCase = checkStatusPaymentUseCase;
        this.sharePreferenceHelper = sharePreferenceHelper;
        this.getUserInforUseCase = getUserInforUseCase;
    }

    @Override
    public void attachView(CheckStatusQRCodeMvpView mvpView) {
        super.attachView(mvpView);
        getUserInfo();
        getPrinterAddress();
    }

    private void getPrinterAddress() {
        if (getMvpView() != null) {
            getMvpView().getPrinterAddressSuccess(sharePreferenceHelper.getPrinterAddress());
        }
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

    protected void autoCheckStatus(Handler handler, int receiptId) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                checkStatus(handler, receiptId, true);
                handler.postDelayed(this, 5000);
            }
        }, 0);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    protected void checkStatus(Handler handler, int receiptId, boolean isAutoCheck) {
        if (!isAutoCheck) {
            getMvpView().showProgressDialog(true);
        }
        long startTime = Calendar.getInstance().getTimeInMillis();
        ClientLog clientLog = new ClientLog(DateUtils.formatDateTime(Calendar.getInstance().getTime(), DateUtils.PATTERN_DD_MM_YYYY_HH_MM_SS),
                Calendar.getInstance().getTimeInMillis(),
                sharePreferenceHelper.getAccessToken(),
                sharePreferenceHelper.getUserName(),
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
                                getMvpView().alreadyPaidSuccess(receipt);
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

    @Override
    public void detachView() {
        checkStatusPaymentUseCase.dispose();
        super.detachView();
    }

    public void savePrinterAddress(BluetoothDevice bluetoothDevice) {
        sharePreferenceHelper.putPrinterName(bluetoothDevice.getName());
        sharePreferenceHelper.putPrinterAddress(bluetoothDevice.getAddress());
    }
}
