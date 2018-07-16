package kh.com.metfone.emoney.eshop.data;

import kh.com.metfone.emoney.eshop.data.models.BaseResult;
import kh.com.metfone.emoney.eshop.data.models.ClientLog;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/16/2018.
 */

public interface ClientLogDataSource {
    Flowable<BaseResult> saveClientLog(String body);

    void saveClientLogLocal(ClientLog clientLog);

    void clearClientLog();

    Flowable<List<ClientLog>> getClientLogFailed();
 }
