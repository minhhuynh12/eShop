package kh.com.metfone.emoney.eshop.data.remote;

import kh.com.metfone.emoney.eshop.consts.Const;
import kh.com.metfone.emoney.eshop.data.ReceiptsDataSource;
import kh.com.metfone.emoney.eshop.data.models.ReceiptsResponse;
import kh.com.metfone.emoney.eshop.utils.SharePreferenceHelper;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/12/2018.
 */
@Singleton
public class ReceiptsRemoteDataSource implements ReceiptsDataSource {
    private final RetrofitService retrofitService;
    private final SharePreferenceHelper sharePreferenceHelper;

    @Inject
    public ReceiptsRemoteDataSource(RetrofitService retrofitService, SharePreferenceHelper sharePreferenceHelper) {
        this.retrofitService = retrofitService;
        this.sharePreferenceHelper = sharePreferenceHelper;
    }


    @Override
    public Flowable<ReceiptsResponse> receipts(String shopId, String fromDate, String toDate) {
        Map<String, String> body = new HashMap<>();
        body.put(Const.KEY_SHOP_ID, shopId);
        body.put(Const.KEY_FROM_DATE, fromDate);
        body.put(Const.KEY_TO_DATE, toDate);
        String accessToken = "Basic " + sharePreferenceHelper.getAccessToken();
        String username = sharePreferenceHelper.getUserName();
        return retrofitService.getReceipts(username, accessToken, sharePreferenceHelper.getLanguage(), body);
    }
}
