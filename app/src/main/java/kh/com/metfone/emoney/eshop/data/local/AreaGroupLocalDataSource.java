package kh.com.metfone.emoney.eshop.data.local;

import kh.com.metfone.emoney.eshop.data.AreaGroupDataSource;
import kh.com.metfone.emoney.eshop.data.models.AreaGroup;

import io.reactivex.Flowable;
import io.realm.Realm;

/**
 * Created by DCV on 3/15/2018.
 */

public class AreaGroupLocalDataSource implements AreaGroupDataSource {

    @Override
    public Flowable<AreaGroup> getAreaGroup() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Flowable<AreaGroup> getAreaGroupLocal() {
        return null;
    }

    @Override
    public void saveAreaGroup(AreaGroup areaGroup) {
        Realm realm = Realm.getDefaultInstance();

        if (!realm.isInTransaction()) {
            realm.beginTransaction();
        }
        realm.insertOrUpdate(areaGroup);
        realm.commitTransaction();
    }
}
