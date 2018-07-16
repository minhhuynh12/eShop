package kh.com.metfone.emoney.eshop.data.local;

import kh.com.metfone.emoney.eshop.data.UserDataSource;
import kh.com.metfone.emoney.eshop.data.models.BaseResult;
import kh.com.metfone.emoney.eshop.data.models.ShopConfig;
import kh.com.metfone.emoney.eshop.data.models.StaffInShop;
import kh.com.metfone.emoney.eshop.data.models.UserInformation;

import io.reactivex.Flowable;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by DCV on 3/9/2018.
 */

public class UserLocalDataSource implements UserDataSource {
    @Override
    public Flowable<UserInformation> login(String userName, String password, String fireBaseToken) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Flowable<UserInformation> getUserInformation() {
        Realm realm = Realm.getDefaultInstance();
        UserInformation userInformation = realm.where(UserInformation.class)
                .findFirst();
        if (userInformation != null) {
            return Flowable.just(realm.copyFromRealm(userInformation));
        } else {
            return Flowable.error(new NullPointerException("Unable to found the information"));
        }
    }

    @Override
    public void saveUserInfo(UserInformation userInformation) {
        Realm realm = Realm.getDefaultInstance();
        if (!realm.isInTransaction()) {
            realm.beginTransaction();
        }
        realm.insertOrUpdate(userInformation);
        realm.commitTransaction();
        realm.close();
    }

    @Override
    public void clearUserInfo() {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> {
            RealmResults<UserInformation> users = realm1.where(UserInformation.class)
                    .findAll();
            users.deleteAllFromRealm();
        });
        realm.close();
    }

    @Override
    public Flowable<Boolean> isLoggedIn() {
        Realm realm = Realm.getDefaultInstance();
        UserInformation userInformation = realm.where(UserInformation.class)
                .findFirst();
        if (userInformation != null) {
            return Flowable.just(true);
        } else {
            return Flowable.just(false);
        }
    }

    @Override
    public Flowable<BaseResult> logout() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Flowable<ShopConfig> getShopConfig() {
        Realm realm = Realm.getDefaultInstance();
        UserInformation userInformation = realm.where(UserInformation.class)
                .findFirst();
        if (userInformation != null && userInformation.getShopConfigs() != null && userInformation.getShopConfigs().size() > 0) {
            return Flowable.just(realm.copyFromRealm(userInformation.getShopConfigs().get(0)));
        } else {
            return Flowable.error(new NullPointerException("Unable to found the information"));
        }
    }

    @Override
    public Flowable<StaffInShop> getStaffInfo(String staffId) {
        Realm realm = Realm.getDefaultInstance();
        StaffInShop staffInShop = realm.where(StaffInShop.class)
                .equalTo(StaffInShop.KEY_STAFF_ID, staffId)
                .findFirst();
        if (staffInShop != null) {
            return Flowable.just(realm.copyFromRealm(staffInShop));
        } else {
            return Flowable.error(new NullPointerException("Unable to found the staff"));
        }
    }

    @Override
    public void saveStaff(StaffInShop staffInShop) {
        Realm realm = Realm.getDefaultInstance();
        if (!realm.isInTransaction()) {
            realm.beginTransaction();
        }
        realm.insertOrUpdate(staffInShop);
        realm.commitTransaction();
        realm.close();
    }
}
