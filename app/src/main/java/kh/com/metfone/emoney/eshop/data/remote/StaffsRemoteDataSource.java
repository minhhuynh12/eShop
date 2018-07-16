package kh.com.metfone.emoney.eshop.data.remote;

import kh.com.metfone.emoney.eshop.data.StaffsDataSource;
import kh.com.metfone.emoney.eshop.data.models.StaffsResponse;
import kh.com.metfone.emoney.eshop.utils.SharePreferenceHelper;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/12/2018.
 */
@Singleton
public class StaffsRemoteDataSource implements StaffsDataSource {
    private final RetrofitService retrofitService;
    private final SharePreferenceHelper sharePreferenceHelper;

    @Inject
    public StaffsRemoteDataSource(RetrofitService retrofitService, SharePreferenceHelper sharePreferenceHelper) {
        this.retrofitService = retrofitService;
        this.sharePreferenceHelper = sharePreferenceHelper;
    }


    @Override
    public Flowable<StaffsResponse> getStaffs() {
        String accessToken = "Basic " + sharePreferenceHelper.getAccessToken();
        String username = sharePreferenceHelper.getUserName();
        return retrofitService.getAllStaffs(username, accessToken, sharePreferenceHelper.getLanguage());
    }
}
