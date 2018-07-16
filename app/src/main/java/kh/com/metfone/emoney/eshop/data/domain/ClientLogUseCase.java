package kh.com.metfone.emoney.eshop.data.domain;

import kh.com.metfone.emoney.eshop.data.ClientLogRepository;
import kh.com.metfone.emoney.eshop.data.executor.PostExecutionThread;
import kh.com.metfone.emoney.eshop.data.executor.ThreadExecutor;
import kh.com.metfone.emoney.eshop.data.models.BaseResult;
import kh.com.metfone.emoney.eshop.data.models.ClientLog;
import kh.com.metfone.emoney.eshop.utils.SharePreferenceHelper;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/16/2018.
 */
public class ClientLogUseCase extends FlowableUseCase<BaseResult, ClientLogUseCase.Param> {
    private final ClientLogRepository clientLogRepository;
    private final SharePreferenceHelper sharePreferenceHelper;

    @Inject
    ClientLogUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, ClientLogRepository clientLogRepository, SharePreferenceHelper sharePreferenceHelper) {
        super(threadExecutor, postExecutionThread);
        this.clientLogRepository = clientLogRepository;
        this.sharePreferenceHelper = sharePreferenceHelper;
    }

    @Override
    Flowable<BaseResult> buildUseCaseObservable(Param param) {
        return clientLogRepository.saveClientLog(param.body);
    }

    public void saveClientLogLocal(ClientLog clientLog) {
        clientLogRepository.saveClientLogLocal(clientLog);
    }

    public void clearClientLogLocal() {
        clientLogRepository.clearClientLog();
        sharePreferenceHelper.putUserName("");
        sharePreferenceHelper.putAccessToken("");

    }

    public static class Param {
        private String body;

        public Param(String body) {
            this.body = body;
        }

        public static Param forParam(String body) {
            return new Param(body);
        }
    }
}
