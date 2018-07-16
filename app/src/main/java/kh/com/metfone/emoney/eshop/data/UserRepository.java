package kh.com.metfone.emoney.eshop.data;

import kh.com.metfone.emoney.eshop.data.models.BaseResult;
import kh.com.metfone.emoney.eshop.data.models.ShopConfig;
import kh.com.metfone.emoney.eshop.data.models.StaffInShop;
import kh.com.metfone.emoney.eshop.data.models.UserInformation;
import kh.com.metfone.emoney.eshop.injection.Local;
import kh.com.metfone.emoney.eshop.injection.Remote;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/9/2018.
 */
@Singleton
public class UserRepository implements UserDataSource {
    private final UserDataSource localUserDataSource;
    private final UserDataSource remoteUserDateSource;

    @Inject
    UserRepository(@Local UserDataSource localUserDataSource,
                   @Remote UserDataSource remoteUserDateSource) {
        this.localUserDataSource = localUserDataSource;
        this.remoteUserDateSource = remoteUserDateSource;
    }

    @Override
    public Flowable<UserInformation> login(String userName, String password, String fireBaseToken) {
        return remoteUserDateSource.login(userName, password, fireBaseToken);
    }

    @Override
    public Flowable<UserInformation> getUserInformation() {
        return localUserDataSource.getUserInformation();
    }

    @Override
    public void saveUserInfo(UserInformation userInformation) {
        localUserDataSource.saveUserInfo(userInformation);

    }

    @Override
    public void clearUserInfo() {
        localUserDataSource.clearUserInfo();
    }

    @Override
    public Flowable<Boolean> isLoggedIn() {
        return localUserDataSource.isLoggedIn();
    }

    @Override
    public Flowable<BaseResult> logout() {
        return remoteUserDateSource.logout();
    }

    @Override
    public Flowable<ShopConfig> getShopConfig() {
        return localUserDataSource.getShopConfig();
    }

    @Override
    public Flowable<StaffInShop> getStaffInfo(String staffId) {
        return localUserDataSource.getStaffInfo(staffId);
    }

    @Override
    public void saveStaff(StaffInShop staffInShop) {
        localUserDataSource.saveStaff(staffInShop);
    }
}
