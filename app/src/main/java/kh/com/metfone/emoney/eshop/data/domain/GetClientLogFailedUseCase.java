package kh.com.metfone.emoney.eshop.data.domain;

import kh.com.metfone.emoney.eshop.data.ClientLogRepository;
import kh.com.metfone.emoney.eshop.data.executor.PostExecutionThread;
import kh.com.metfone.emoney.eshop.data.executor.ThreadExecutor;
import kh.com.metfone.emoney.eshop.data.models.ClientLog;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/21/2018.
 */

public class GetClientLogFailedUseCase extends FlowableUseCase<List<ClientLog>, GetClientLogFailedUseCase.Param> {
    private final ClientLogRepository clientLogRepository;

    @Inject
    GetClientLogFailedUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, ClientLogRepository clientLogRepository) {
        super(threadExecutor, postExecutionThread);
        this.clientLogRepository = clientLogRepository;
    }

    @Override
    Flowable<List<ClientLog>> buildUseCaseObservable(Param param) {
        return clientLogRepository.getClientLogFailed();
    }

    public class Param {
    }
}
