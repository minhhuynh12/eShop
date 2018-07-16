package kh.com.metfone.emoney.eshop.data.remote;

import kh.com.metfone.emoney.eshop.data.StaffResetPasswordDataSource;
import kh.com.metfone.emoney.eshop.data.models.StaffResetPassResponse;
import kh.com.metfone.emoney.eshop.utils.SharePreferenceHelper;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/15/2018.
 */
@Singleton
public class StaffResetPasswordRemoteDataSource implements StaffResetPasswordDataSource {
    private final RetrofitService retrofitService;
    private final SharePreferenceHelper sharePreferenceHelper;

    @Inject
    public StaffResetPasswordRemoteDataSource(RetrofitService retrofitService, SharePreferenceHelper sharePreferenceHelper) {
        this.retrofitService = retrofitService;
        this.sharePreferenceHelper = sharePreferenceHelper;
    }
    @Override
    public Flowable<StaffResetPassResponse> resetPasswordStaff(int staffId) {
        String accessToken = "Basic " + sharePreferenceHelper.getAccessToken();
        return retrofitService.resetPasswordStaff(sharePreferenceHelper.getUserName(), accessToken, sharePreferenceHelper.getLanguage(), staffId);
    }
}
