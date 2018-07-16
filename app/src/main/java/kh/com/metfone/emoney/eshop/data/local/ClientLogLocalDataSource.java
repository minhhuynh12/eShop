package kh.com.metfone.emoney.eshop.data.local;

import kh.com.metfone.emoney.eshop.data.ClientLogDataSource;
import kh.com.metfone.emoney.eshop.data.models.BaseResult;
import kh.com.metfone.emoney.eshop.data.models.ClientLog;

import java.util.List;

import io.reactivex.Flowable;
import io.realm.Realm;

/**
 * Created by DCV on 3/16/2018.
 */

public class ClientLogLocalDataSource implements ClientLogDataSource {
    @Override
    public Flowable<BaseResult> saveClientLog(String body) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void saveClientLogLocal(ClientLog clientLog) {
        Realm realm = Realm.getDefaultInstance();
        if (!realm.isInTransaction()) {
            realm.beginTransaction();
        }
        realm.insertOrUpdate(clientLog);
        realm.commitTransaction();
        realm.close();
    }

    @Override
    public void clearClientLog() {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 ->
                realm1.where(ClientLog.class)
                        .findAll().deleteAllFromRealm());
        realm.close();
    }

    @Override
    public Flowable<List<ClientLog>> getClientLogFailed() {
        Realm realm = Realm.getDefaultInstance();
        List<ClientLog> clientLogs = realm.where(ClientLog.class)
                .equalTo(ClientLog.IS_lOG_SERVER_SUCCESS, false)
                .findAll();
        if (clientLogs != null && clientLogs.size() > 0) {
            clientLogs = realm.copyFromRealm(clientLogs);
            realm.close();
            return Flowable.just(clientLogs);
        } else {
            realm.close();
            return Flowable.error(new Throwable("Empty client log failed"));
        }
    }
}
