package kh.com.metfone.emoney.eshop.data.remote;

import kh.com.metfone.emoney.eshop.consts.Const;
import kh.com.metfone.emoney.eshop.data.ChangePasswordDataSource;
import kh.com.metfone.emoney.eshop.data.models.BaseResult;
import kh.com.metfone.emoney.eshop.utils.SharePreferenceHelper;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/15/2018.
 */
@Singleton
public class ChangePasswordRemoteDataSource implements ChangePasswordDataSource {
    private final RetrofitService retrofitService;
    private final SharePreferenceHelper sharePreferenceHelper;

    @Inject
    public ChangePasswordRemoteDataSource(RetrofitService retrofitService, SharePreferenceHelper sharePreferenceHelper) {
        this.retrofitService = retrofitService;
        this.sharePreferenceHelper = sharePreferenceHelper;
    }

    @Override
    public Flowable<BaseResult> changePassword(String oldPassword, String newPassword) {
        String accessToken = "Basic " + sharePreferenceHelper.getAccessToken();
        String username = sharePreferenceHelper.getUserName();
        Map<String, String> body = new HashMap<>();
        body.put(Const.KEY_OLD_PASSWORD, oldPassword);
        body.put(Const.KEY_NEW_PASSWORD, newPassword);
        return retrofitService.changePassword(username, accessToken, sharePreferenceHelper.getLanguage(), body);
    }
}
