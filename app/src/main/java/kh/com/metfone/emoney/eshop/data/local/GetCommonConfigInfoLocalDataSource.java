package kh.com.metfone.emoney.eshop.data.local;

import kh.com.metfone.emoney.eshop.data.GetCommonConfigInfoDataSource;
import kh.com.metfone.emoney.eshop.data.models.BaseResult;
import kh.com.metfone.emoney.eshop.data.models.CommonConfigInfo;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.internal.ManagableObject;

/**
 * Created by DCV on 3/9/2018.
 */

public class GetCommonConfigInfoLocalDataSource implements GetCommonConfigInfoDataSource {
    @Override
    public Flowable<CommonConfigInfo> getCommonConfigInformation() {
        return null;
    }

    @Override
    public Flowable<CommonConfigInfo> getCommonConfigInfo() {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(CommonConfigInfo.class)
                .findFirstAsync()
                .asFlowable()
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(ManagableObject::isValid)
                .filter(setting -> setting.isLoaded())
                .map(realm::copyFromRealm)
                .cast(CommonConfigInfo.class)
                .doOnCancel(realm::close);


        /*try (Realm realm = Realm.getDefaultInstance()) {
            CommonConfigInfo info = realm.where(CommonConfigInfo.class)
                    .findFirst();
            if (info != null) {
                return Flowable.just(realm.copyFromRealm(info));
            } else {
                return Flowable.error(new NullPointerException("Unable to found the information"));
            }
        }*/
    }

    @Override
    public Flowable<BaseResult> changeExchangeRate(String khrMoney) {
        return null;
    }

    @Override
    public void saveCommonConfigInfo(CommonConfigInfo commonConfigInfo) {
        Realm realm = Realm.getDefaultInstance();
       /* realm.executeTransaction(realm1 -> {
            realm1.where(CommonConfigInfo.class)
                    .findAll()
                    .deleteAllFromRealm();
            realm1.copyToRealmOrUpdate(commonConfigInfo);
        });*/

        if (!realm.isInTransaction()) {
            realm.beginTransaction();
        }
        realm.insertOrUpdate(commonConfigInfo);
        realm.commitTransaction();
//        realm.close();

    }

    @Override
    public void clearCommonConfigInfo() {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> {
            RealmResults<CommonConfigInfo> infos = realm1.where(CommonConfigInfo.class)
                    .findAll();
            infos.deleteAllFromRealm();
        });
        realm.close();
    }

}
