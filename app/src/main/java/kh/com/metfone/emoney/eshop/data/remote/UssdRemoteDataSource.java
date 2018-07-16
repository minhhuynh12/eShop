package kh.com.metfone.emoney.eshop.data.remote;

import kh.com.metfone.emoney.eshop.data.USSDDataSource;
import kh.com.metfone.emoney.eshop.data.models.UssdResponse;
import kh.com.metfone.emoney.eshop.utils.SharePreferenceHelper;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/15/2018.
 */
@Singleton
public class UssdRemoteDataSource implements USSDDataSource {
    private final RetrofitService retrofitService;
    private final SharePreferenceHelper sharePreferenceHelper;

    @Inject
    public UssdRemoteDataSource(RetrofitService retrofitService, SharePreferenceHelper sharePreferenceHelper) {
        this.retrofitService = retrofitService;
        this.sharePreferenceHelper = sharePreferenceHelper;
    }

    @Override
    public Flowable<UssdResponse> confirmByPhone(int receiptId) {
        String accessToken = "Basic " + sharePreferenceHelper.getAccessToken();
        String username = sharePreferenceHelper.getUserName();
        return retrofitService.confirmByPhone(username, accessToken, receiptId);
    }
}
