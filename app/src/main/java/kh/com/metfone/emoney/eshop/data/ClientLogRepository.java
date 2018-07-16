package kh.com.metfone.emoney.eshop.data;

import kh.com.metfone.emoney.eshop.data.models.BaseResult;
import kh.com.metfone.emoney.eshop.data.models.ClientLog;
import kh.com.metfone.emoney.eshop.injection.Local;
import kh.com.metfone.emoney.eshop.injection.Remote;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/16/2018.
 */

public class ClientLogRepository implements ClientLogDataSource {

    private final ClientLogDataSource clientLogLocalDataSource;
    private final ClientLogDataSource clientLogRemoteDataSource;

    @Inject
    ClientLogRepository(@Local ClientLogDataSource clientLogLocalDataSource,
                        @Remote ClientLogDataSource clientLogRemoteDataSource) {
        this.clientLogLocalDataSource = clientLogLocalDataSource;
        this.clientLogRemoteDataSource = clientLogRemoteDataSource;
    }

    @Override
    public Flowable<BaseResult> saveClientLog(String body) {
        return clientLogRemoteDataSource.saveClientLog(body);
    }

    @Override
    public void saveClientLogLocal(ClientLog clientLog) {
        clientLogLocalDataSource.saveClientLogLocal(clientLog);
    }

    @Override
    public void clearClientLog() {
        clientLogLocalDataSource.clearClientLog();
    }

    @Override
    public Flowable<List<ClientLog>> getClientLogFailed() {
        return clientLogLocalDataSource.getClientLogFailed();
    }
}
