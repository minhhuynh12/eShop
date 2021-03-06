package kh.com.metfone.emoney.eshop.data.remote;

import kh.com.metfone.emoney.eshop.data.CheckStatusPaymentDataSource;
import kh.com.metfone.emoney.eshop.data.models.Receipt;
import kh.com.metfone.emoney.eshop.utils.SharePreferenceHelper;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/15/2018.
 */
@Singleton
public class CheckStatusPaymentRemoteDataSource implements CheckStatusPaymentDataSource {
    private final RetrofitService retrofitService;
    private final SharePreferenceHelper sharePreferenceHelper;

    @Inject
    public CheckStatusPaymentRemoteDataSource(RetrofitService retrofitService, SharePreferenceHelper sharePreferenceHelper) {
        this.retrofitService = retrofitService;
        this.sharePreferenceHelper = sharePreferenceHelper;
    }
    @Override
    public Flowable<Receipt> checkStatus(int receiptId) {
        String accessToken = "Basic " + sharePreferenceHelper.getAccessToken();
        return retrofitService.getReceiptInfor(sharePreferenceHelper.getUserName(), accessToken, sharePreferenceHelper.getLanguage(), receiptId);
    }
}
