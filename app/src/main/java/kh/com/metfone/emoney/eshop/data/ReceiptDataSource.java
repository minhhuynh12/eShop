package kh.com.metfone.emoney.eshop.data;

import kh.com.metfone.emoney.eshop.data.models.Receipt;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/13/2018.
 */

public interface ReceiptDataSource {
    Flowable<Receipt> newReceipt(String receiptType, String invoiceTitle, String custPhoneNumber, String currencyCode, String amount, String discountType, String discount);
    Flowable<Receipt> newReceipt(String receiptType, String invoiceTitle, String custPhoneNumber, String currencyCode, String amount, String discountType, String discount,String paymentCurrencyCode);
    Flowable<Receipt> getReceiptInfor(int receiptId);
}
