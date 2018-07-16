package kh.com.metfone.emoney.eshop.data;

import kh.com.metfone.emoney.eshop.data.models.Receipt;
import kh.com.metfone.emoney.eshop.injection.Local;
import kh.com.metfone.emoney.eshop.injection.Remote;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/13/2018.
 */
@Singleton
public class ReceiptRepository implements ReceiptDataSource {

    private final ReceiptDataSource localReceiptDataSource;
    private final ReceiptDataSource remoteReceiptDataSource;

    @Inject
    ReceiptRepository(@Local ReceiptDataSource localReceiptDataSource,
                      @Remote ReceiptDataSource remoteReceiptDataSource) {
        this.localReceiptDataSource = localReceiptDataSource;
        this.remoteReceiptDataSource = remoteReceiptDataSource;
    }

    @Override
    public Flowable<Receipt> newReceipt(String receiptType, String invoiceTitle, String custPhoneNumber, String currencyCode, String amount, String discountType, String discount) {
        return remoteReceiptDataSource.newReceipt(receiptType, invoiceTitle, custPhoneNumber, currencyCode, amount, discountType, discount);
    }
    @Override
    public Flowable<Receipt> newReceipt(String receiptType, String invoiceTitle, String custPhoneNumber, String currencyCode, String amount, String discountType, String discount,String paymentCurrencyCode) {
        return remoteReceiptDataSource.newReceipt(receiptType, invoiceTitle, custPhoneNumber, currencyCode, amount, discountType, discount,paymentCurrencyCode);
    }
    @Override
    public Flowable<Receipt> getReceiptInfor(int receiptId) {
        return null;
    }
}
