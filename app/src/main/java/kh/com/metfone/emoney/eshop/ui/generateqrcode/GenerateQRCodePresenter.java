package kh.com.metfone.emoney.eshop.ui.generateqrcode;

import kh.com.metfone.emoney.eshop.R;
import kh.com.metfone.emoney.eshop.consts.Const;
import kh.com.metfone.emoney.eshop.data.domain.ClientLogUseCase;
import kh.com.metfone.emoney.eshop.data.domain.GetLocalCommonConfigUseCase;
import kh.com.metfone.emoney.eshop.data.domain.GetShopConfigUseCase;
import kh.com.metfone.emoney.eshop.data.domain.GetUserInforUseCase;
import kh.com.metfone.emoney.eshop.data.domain.LogoutUseCase;
import kh.com.metfone.emoney.eshop.data.domain.NewReceiptUseCase;
import kh.com.metfone.emoney.eshop.data.models.ClientLog;
import kh.com.metfone.emoney.eshop.data.models.CommonConfigInfo;
import kh.com.metfone.emoney.eshop.data.models.Receipt;
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
 * Created by DCV on 3/6/2018.
 */

public class GenerateQRCodePresenter extends BaseSaveLogCheckAuthenPresenter<GenerateQRCodeMvpView> {
    private final NewReceiptUseCase newReceiptUseCase;
    private final SharePreferenceHelper sharePreferenceHelper;
    private final GetLocalCommonConfigUseCase getLocalCommonConfigUseCase;
    private final GetShopConfigUseCase getShopConfigUseCase;
    private final GetUserInforUseCase getUserInforUseCase;

    @Inject
    GenerateQRCodePresenter(NewReceiptUseCase newReceiptUseCase,
                            ClientLogUseCase clientLogUseCase,
                            LogoutUseCase logoutUseCase,
                            SharePreferenceHelper sharePreferenceHelper,
                            GetLocalCommonConfigUseCase getLocalCommonConfigUseCase,
                            GetShopConfigUseCase getShopConfigUseCase,
                            GetUserInforUseCase getUserInforUseCase) {
        super(clientLogUseCase, logoutUseCase);
        this.newReceiptUseCase = newReceiptUseCase;
        this.sharePreferenceHelper = sharePreferenceHelper;
        this.getLocalCommonConfigUseCase = getLocalCommonConfigUseCase;
        this.getShopConfigUseCase = getShopConfigUseCase;
        this.getUserInforUseCase = getUserInforUseCase;
    }

    @Override
    public void attachView(GenerateQRCodeMvpView mvpView) {
        super.attachView(mvpView);
        getShopConfig();
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

    protected void newReceipt(String staffCode, String receiptType, String invoiceTitle, String currencyCode, String amount, String discountType, String discount, String paymentCurrencyCode ) {
        long startTime = Calendar.getInstance().getTimeInMillis();
        if (getMvpView() != null) {
            getMvpView().showProgressDialog(true);
        }
        ClientLog clientLog = new ClientLog(DateUtils.formatDateTime(Calendar.getInstance().getTime(), DateUtils.PATTERN_DD_MM_YYYY_HH_MM_SS),
                Calendar.getInstance().getTimeInMillis(),
                sharePreferenceHelper.getAccessToken(),
                staffCode,
                "payment/receipt",
                GenerateQRCodePresenter.class.getName(),
                "newReceipt");
        newReceiptUseCase.execute(new DisposableSubscriber<Receipt>() {
            @Override
            public void onNext(Receipt receipt) {
                long endTime = Calendar.getInstance().getTimeInMillis();
                clientLog.setEndTime(DateUtils.formatDateTime(Calendar.getInstance().getTime(), DateUtils.PATTERN_DD_MM_YYYY_HH_MM_SS));
                clientLog.setDuration(endTime - startTime);

                if (getMvpView() != null) {
                    getMvpView().showProgressDialog(false);
                    if (Const.VALUE_SUCCESS_CODE == receipt.getStatus()) {
                        getMvpView().newReceiptSuccess(receipt);
                    } else {
                        getMvpView().newReceiptFailed(receipt.getMessage());
                    }
                }
                clientLog.setStatus(receipt.getStatus());
                String requestContent = new StringBuilder(Const.KEY_RECEIPT_TYPE)
                        .append(":")
                        .append(receiptType)
                        .append("|")
                        .append(Const.KEY_INVOICE_TITLE)
                        .append(":")
                        .append(invoiceTitle)
                        .append("|")
                        .append(Const.KEY_CURRENCY_CODE)
                        .append(":")
                        .append(currencyCode)
                        .append("|")
                        .append(Const.KEY_AMOUNT)
                        .append(":")
                        .append(amount)
                        .append("|")
                        .append(Const.PAYMNET_CURRENCY_CODE)
                        .append(":")
                        .append(paymentCurrencyCode)
                        .append("|")
                        .append(Const.KEY_DISCOUNT_TYPE)
                        .append(":")
                        .append(discountType)
                        .append("|")
                        .append(Const.KEY_DISCOUNT)
                        .append(":")
                        .append(discount)
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
                        getMvpView().showProgressDialog(false);
                        if (t instanceof SocketTimeoutException || t instanceof SSLException) {
                            getMvpView().notifyError(R.string.connect_timeout);
                        } else {
                            getMvpView().newReceiptFailed(t.getMessage());
                        }
                    }
                }
                onComplete();
            }

            @Override
            public void onComplete() {
                saveLog(clientLog);
            }
        }, NewReceiptUseCase.Params.forParams(receiptType, invoiceTitle, null, currencyCode, amount, discountType, discount , paymentCurrencyCode));

    }

    private void getShopConfig() {
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

    private void getCommonConfig() {
        getLocalCommonConfigUseCase.execute(new DisposableSubscriber<CommonConfigInfo>() {
            @Override
            public void onNext(CommonConfigInfo commonConfigInfo) {
                if (getMvpView() != null) {
                    getMvpView().getConfigSuccess(commonConfigInfo);
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

    @Override
    public void detachView() {
        newReceiptUseCase.dispose();
        super.detachView();
    }
}
