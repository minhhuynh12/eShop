package kh.com.metfone.emoney.eshop.data.remote;

import kh.com.metfone.emoney.eshop.consts.Const;
import kh.com.metfone.emoney.eshop.data.UserDataSource;
import kh.com.metfone.emoney.eshop.data.models.BaseResult;
import kh.com.metfone.emoney.eshop.data.models.ShopConfig;
import kh.com.metfone.emoney.eshop.data.models.StaffInShop;
import kh.com.metfone.emoney.eshop.data.models.UserInformation;
import kh.com.metfone.emoney.eshop.utils.SharePreferenceHelper;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/9/2018.
 */
@Singleton
public class UserRemoteDataSource implements UserDataSource {
    private final RetrofitService retrofitService;
    private final SharePreferenceHelper sharePreferenceHelper;
    @Inject
    public UserRemoteDataSource(RetrofitService retrofitService, SharePreferenceHelper sharePreferenceHelper) {
        this.retrofitService = retrofitService;
        this.sharePreferenceHelper = sharePreferenceHelper;
    }

    @Override
    public Flowable<UserInformation> login(String userName,  String password, String firebaseToken) {
        Map<String, String> body = new HashMap<>();
        body.put(Const.KEY_USERNAME, userName);
        body.put(Const.KEY_PASSWORD, password);
        body.put(Const.KEY_PLATFORM, Const.VALUE_ANDROID_PLATFORM);

        return retrofitService.login(sharePreferenceHelper.getLanguage(), firebaseToken, body);
    }

    @Override
    public Flowable<UserInformation> getUserInformation() {
        throw  new UnsupportedOperationException();
    }

    @Override
    public void saveUserInfo(UserInformation userInformation) {
        throw  new UnsupportedOperationException();
    }

    @Override
    public void clearUserInfo() {
        throw  new UnsupportedOperationException();
    }

    @Override
    public Flowable<Boolean> isLoggedIn() {
        throw  new UnsupportedOperationException();
    }

    @Override
    public Flowable<BaseResult> logout() {
        String accessToken = "Basic " + sharePreferenceHelper.getAccessToken();
        String username = sharePreferenceHelper.getUserName();
        String language = sharePreferenceHelper.getLanguage();
        return retrofitService.logout(username, accessToken, language);
    }

    @Override
    public Flowable<ShopConfig> getShopConfig() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Flowable<StaffInShop> getStaffInfo(String staffId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void saveStaff(StaffInShop staffInShop) {
        throw new UnsupportedOperationException();
    }
}
