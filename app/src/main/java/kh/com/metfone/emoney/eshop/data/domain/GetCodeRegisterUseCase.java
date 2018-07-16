package kh.com.metfone.emoney.eshop.data.domain;

import kh.com.metfone.emoney.eshop.data.GetCodeRegisterRepository;
import kh.com.metfone.emoney.eshop.data.executor.PostExecutionThread;
import kh.com.metfone.emoney.eshop.data.executor.ThreadExecutor;
import kh.com.metfone.emoney.eshop.data.models.BaseResult;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/12/2018.
 */

public class GetCodeRegisterUseCase extends FlowableUseCase<BaseResult, GetCodeRegisterUseCase.Param> {

    private final GetCodeRegisterRepository repository;

    @Inject
    GetCodeRegisterUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, GetCodeRegisterRepository repository) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }

    @Override
    Flowable<BaseResult> buildUseCaseObservable(Param param) {
        return repository.getRegisterCode(param.phoneNumber);
    }

    public static class Param {
        private String phoneNumber;

        public Param(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public static Param forParam(String phoneNumber) {
            return new Param(phoneNumber);
        }

    }
}
