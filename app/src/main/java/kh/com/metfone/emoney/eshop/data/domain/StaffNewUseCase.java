package kh.com.metfone.emoney.eshop.data.domain;

import kh.com.metfone.emoney.eshop.data.StaffNewRepository;
import kh.com.metfone.emoney.eshop.data.executor.PostExecutionThread;
import kh.com.metfone.emoney.eshop.data.executor.ThreadExecutor;
import kh.com.metfone.emoney.eshop.data.models.BaseResult;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/12/2018.
 */

public class StaffNewUseCase extends FlowableUseCase<BaseResult, StaffNewUseCase.Param> {

    private final StaffNewRepository repository;

    @Inject
    StaffNewUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, StaffNewRepository repository) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }
    @Override
    Flowable<BaseResult> buildUseCaseObservable(Param param) {
        return repository.newStaff(param.staffCode, param.staffName, param.msisdn );
    }

    public static class Param {
        private String staffCode;
        private String staffName;
        private String msisdn;

        public Param(String staff_code, String staff_name, String msidn) {
            this.staffCode = staff_code;
            this.staffName = staff_name;
            this.msisdn = msidn;
        }

        public static Param forParam(String staffCode, String staffName, String msisdn ) {
            return new Param(staffCode, staffName, msisdn);
        }

    }
}
