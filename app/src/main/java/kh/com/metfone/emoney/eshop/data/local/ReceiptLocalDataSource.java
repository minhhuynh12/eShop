package kh.com.metfone.emoney.eshop.data.local;

import kh.com.metfone.emoney.eshop.data.ReceiptDataSource;
import kh.com.metfone.emoney.eshop.data.models.Receipt;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/13/2018.
 */

public class ReceiptLocalDataSource implements ReceiptDataSource {

    @Override
    public Flowable<Receipt> newReceipt(String receiptType, String invoiceTitle, String  custPhoneNumber, String currencyCode, String amount, String discountType, String discount) {
        return null;
    }
    @Override
    public Flowable<Receipt> newReceipt(String receiptType, String invoiceTitle, String  custPhoneNumber, String currencyCode, String amount, String discountType, String discount, String paymentCurrencyCode) {
        return null;
    }
    @Override
    public Flowable<Receipt> getReceiptInfor(int receiptId) {
        return null;
    }
}
