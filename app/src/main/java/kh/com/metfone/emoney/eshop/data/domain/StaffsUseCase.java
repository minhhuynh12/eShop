package kh.com.metfone.emoney.eshop.data.domain;

import kh.com.metfone.emoney.eshop.data.StaffsRepository;
import kh.com.metfone.emoney.eshop.data.executor.PostExecutionThread;
import kh.com.metfone.emoney.eshop.data.executor.ThreadExecutor;
import kh.com.metfone.emoney.eshop.data.models.StaffsResponse;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/12/2018.
 */

public class StaffsUseCase extends FlowableUseCase<StaffsResponse, StaffsUseCase.Params> {

    private final StaffsRepository repository;

    @Inject
    StaffsUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, StaffsRepository repository) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }

    @Override
    Flowable<StaffsResponse> buildUseCaseObservable(Params param) {
        return repository.getStaffs();
    }


    public static class Params {

        public Params() {
        }

        public static Params forParam() {
            return new Params();
        }

    }
}
