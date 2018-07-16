package kh.com.metfone.emoney.eshop.data.domain;

import kh.com.metfone.emoney.eshop.data.AreaGroupRepository;
import kh.com.metfone.emoney.eshop.data.executor.PostExecutionThread;
import kh.com.metfone.emoney.eshop.data.executor.ThreadExecutor;
import kh.com.metfone.emoney.eshop.data.models.AreaGroup;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/15/2018.
 */

public class GetAreaGroupLocalUseCase extends FlowableUseCase<AreaGroup, GetAreaGroupLocalUseCase.Params> {
    private final AreaGroupRepository areaGroupRepository;

    @Inject
    GetAreaGroupLocalUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, AreaGroupRepository areaGroupRepository) {
        super(threadExecutor, postExecutionThread);
        this.areaGroupRepository = areaGroupRepository;
    }

    @Override
    Flowable<AreaGroup> buildUseCaseObservable(Params params) {
        return areaGroupRepository.getAreaGroup();
    }

    public static class Params {
    }
}
