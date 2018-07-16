package kh.com.metfone.emoney.eshop.data.domain;

import kh.com.metfone.emoney.eshop.data.ReceiptRepository;
import kh.com.metfone.emoney.eshop.data.executor.PostExecutionThread;
import kh.com.metfone.emoney.eshop.data.executor.ThreadExecutor;
import kh.com.metfone.emoney.eshop.data.models.Receipt;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/13/2018.
 */

public class NewReceiptUseCase extends FlowableUseCase<Receipt, NewReceiptUseCase.Params> {
    private final ReceiptRepository receiptRepository;

    @Inject
    NewReceiptUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, ReceiptRepository receiptRepository) {
        super(threadExecutor, postExecutionThread);
        this.receiptRepository = receiptRepository;
    }

    @Override
    Flowable<Receipt> buildUseCaseObservable(Params params) {
        return receiptRepository.newReceipt(params.receiptType, params.invoiceTitle, params.custPhoneNumber, params.currencyCode, params.amount, params.discountType, params.discount,params.paymentCurrencyCode);
    }

    public static class Params {
        private String receiptType;
        private String invoiceTitle;
        private String custPhoneNumber;
        private String currencyCode;
        private String amount;
        private String discountType;
        private String discount;
        private String paymentCurrencyCode;
        public Params(String receiptType, String invoiceTitle, String phonenumber, String currencyCode, String amount, String discountType, String discount) {
            this.receiptType = receiptType;
            this.invoiceTitle = invoiceTitle;
            this.custPhoneNumber = phonenumber;
            this.currencyCode = currencyCode;
            this.amount = amount;
            this.discountType = discountType;
            this.discount = discount;
        }
        public Params(String receiptType, String invoiceTitle, String phonenumber, String currencyCode, String amount, String discountType, String discount,String paymentCurrencyCode) {
            this.receiptType = receiptType;
            this.invoiceTitle = invoiceTitle;
            this.custPhoneNumber = phonenumber;
            this.currencyCode = currencyCode;
            this.amount = amount;
            this.discountType = discountType;
            this.discount = discount;
            this.paymentCurrencyCode = paymentCurrencyCode;
        }
        public static Params forParams(String receiptType, String invoiceTitle, String custPhoneNumber, String currencyCode, String amount, String discountType, String discount) {
            return new Params(receiptType, invoiceTitle, custPhoneNumber, currencyCode, amount, discountType, discount);
        }
        public static Params forParams(String receiptType, String invoiceTitle, String custPhoneNumber, String currencyCode, String amount, String discountType, String discount, String paymentCurrencyCode) {
            return new Params(receiptType, invoiceTitle, custPhoneNumber, currencyCode, amount, discountType, discount,paymentCurrencyCode);
        }
    }
}
