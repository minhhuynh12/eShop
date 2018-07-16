package kh.com.metfone.emoney.eshop.data.domain;

import kh.com.metfone.emoney.eshop.data.GetCommonConfigInfoRepository;
import kh.com.metfone.emoney.eshop.data.executor.PostExecutionThread;
import kh.com.metfone.emoney.eshop.data.executor.ThreadExecutor;
import kh.com.metfone.emoney.eshop.data.models.CommonConfigInfo;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/9/2018.
 */

public class GetCommonConfigUseCase extends FlowableUseCase<CommonConfigInfo, GetCommonConfigUseCase.Params> {
    private final GetCommonConfigInfoRepository getConfigInfoRepository;

    @Inject
    GetCommonConfigUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, GetCommonConfigInfoRepository getConfigInfoRepository) {
        super(threadExecutor, postExecutionThread);
        this.getConfigInfoRepository = getConfigInfoRepository;
    }

    @Override
    Flowable<CommonConfigInfo> buildUseCaseObservable(Params params) {
        return getConfigInfoRepository.getCommonConfigInformation();
    }

    public void saveCommonConfigInfo(CommonConfigInfo info) {
        getConfigInfoRepository.saveCommonConfigInfo(info);
    }

    public Flowable<CommonConfigInfo> getCommonConfigInfo() {
        return  getConfigInfoRepository.getCommonConfigInfo();
    }

    public static final class Params {

    /*    public Params() {
        }

        public static Params forParams() {
            return new Params();
        }*/
    }
}
