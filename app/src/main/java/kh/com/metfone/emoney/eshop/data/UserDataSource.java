package kh.com.metfone.emoney.eshop.data;

import kh.com.metfone.emoney.eshop.data.models.BaseResult;
import kh.com.metfone.emoney.eshop.data.models.ShopConfig;
import kh.com.metfone.emoney.eshop.data.models.StaffInShop;
import kh.com.metfone.emoney.eshop.data.models.UserInformation;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/9/2018.
 */

public interface UserDataSource {
    Flowable<UserInformation> login(String userName, String password, String fireBaseToken);

    Flowable<UserInformation> getUserInformation();

    void saveUserInfo(UserInformation userInformation);

    void clearUserInfo();

    Flowable<Boolean> isLoggedIn();

    Flowable<BaseResult> logout();

    Flowable<ShopConfig> getShopConfig();

    Flowable<StaffInShop> getStaffInfo(String staffId);

    void saveStaff(StaffInShop staffInShop);


}
