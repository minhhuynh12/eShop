package kh.com.metfone.emoney.eshop.data.domain;

import kh.com.metfone.emoney.eshop.data.GetCommonConfigInfoRepository;
import kh.com.metfone.emoney.eshop.data.executor.PostExecutionThread;
import kh.com.metfone.emoney.eshop.data.executor.ThreadExecutor;
import kh.com.metfone.emoney.eshop.data.models.CommonConfigInfo;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/19/2018.
 */

public class GetLocalCommonConfigUseCase extends FlowableUseCase<CommonConfigInfo, GetLocalCommonConfigUseCase.Param> {
    private final GetCommonConfigInfoRepository getCommonConfigInfoRepository;

    @Inject
    GetLocalCommonConfigUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, GetCommonConfigInfoRepository getCommonConfigInfoRepository) {
        super(threadExecutor, postExecutionThread);
        this.getCommonConfigInfoRepository = getCommonConfigInfoRepository;
    }

    @Override
    Flowable<CommonConfigInfo> buildUseCaseObservable(Param param) {
        return getCommonConfigInfoRepository.getCommonConfigInfo();
    }

    public class Param {
    }
}
