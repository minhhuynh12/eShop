package kh.com.metfone.emoney.eshop.data.remote;

import kh.com.metfone.emoney.eshop.consts.Const;
import kh.com.metfone.emoney.eshop.data.ReceiptDataSource;
import kh.com.metfone.emoney.eshop.data.models.Receipt;
import kh.com.metfone.emoney.eshop.utils.SharePreferenceHelper;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/13/2018.
 */

public class ReceiptRemoteDataSource implements ReceiptDataSource {
    private final RetrofitService retrofitService;
    private final SharePreferenceHelper sharePreferenceHelper;

    @Inject
    public ReceiptRemoteDataSource(RetrofitService retrofitService, SharePreferenceHelper sharePreferenceHelper) {
        this.retrofitService = retrofitService;
        this.sharePreferenceHelper = sharePreferenceHelper;
    }

    @Override
    public Flowable<Receipt> newReceipt(String receiptType, String invoiceTitle, String custPhoneNumber, String currencyCode, String amount, String discountType, String discount) {
        Map<String, String> body = new HashMap<>();
        body.put(Const.KEY_RECEIPT_TYPE, receiptType);
        body.put(Const.KEY_INVOICE_TITLE, invoiceTitle);
        body.put(Const.KEY_CURRENCY_CODE, currencyCode);
        body.put(Const.KEY_AMOUNT, amount);
        body.put(Const.KEY_DISCOUNT_TYPE, discountType);
        body.put(Const.KEY_DISCOUNT, discount);
        if (custPhoneNumber != null) {
            body.put(Const.KEY_PHONE_CUSTOMER, custPhoneNumber);
        }
        String accessToken = "Basic " + sharePreferenceHelper.getAccessToken();
        String username = sharePreferenceHelper.getUserName();
        return retrofitService.newReceipt(username, accessToken, sharePreferenceHelper.getLanguage(), body);
    }
    @Override
    public Flowable<Receipt> newReceipt(String receiptType, String invoiceTitle, String custPhoneNumber, String currencyCode, String amount, String discountType, String discount, String paymentCurrencyCode) {
        Map<String, String> body = new HashMap<>();
        body.put(Const.KEY_RECEIPT_TYPE, receiptType);
        body.put(Const.KEY_INVOICE_TITLE, invoiceTitle);
        body.put(Const.KEY_CURRENCY_CODE, currencyCode);
        body.put(Const.KEY_AMOUNT, amount);
        body.put(Const.KEY_DISCOUNT_TYPE, discountType);
        body.put(Const.KEY_DISCOUNT, discount);
        if (custPhoneNumber != null) {
            body.put(Const.KEY_PHONE_CUSTOMER, custPhoneNumber);
        }
        body.put(Const.PAYMNET_CURRENCY_CODE,paymentCurrencyCode);
        String accessToken = "Basic " + sharePreferenceHelper.getAccessToken();
        String username = sharePreferenceHelper.getUserName();
        return retrofitService.newReceipt(username, accessToken, sharePreferenceHelper.getLanguage(), body);
    }
    @Override
    public Flowable<Receipt> getReceiptInfor(int receiptId) {
        return null;
    }
}
