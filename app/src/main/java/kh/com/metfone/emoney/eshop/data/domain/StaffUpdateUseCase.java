package kh.com.metfone.emoney.eshop.data.domain;

import kh.com.metfone.emoney.eshop.data.StaffUpdateRepository;
import kh.com.metfone.emoney.eshop.data.executor.PostExecutionThread;
import kh.com.metfone.emoney.eshop.data.executor.ThreadExecutor;
import kh.com.metfone.emoney.eshop.data.models.BaseResult;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/12/2018.
 */

public class StaffUpdateUseCase extends FlowableUseCase<BaseResult, StaffUpdateUseCase.Param> {

    private final StaffUpdateRepository repository;

    @Inject
    StaffUpdateUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, StaffUpdateRepository repository) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }
    @Override
    Flowable<BaseResult> buildUseCaseObservable(Param param) {
        return repository.updateStaff(param.staffId, param.staffName, param.msisdn );
    }

    public static class Param {
        private String staffId;
        private String staffName;
        private String msisdn;

        public Param(String staff_code, String staff_name, String msidn) {
            this.staffId = staff_code;
            this.staffName = staff_name;
            this.msisdn = msidn;
        }

        public static Param forParam(String staffId, String staffName, String msisdn ) {
            return new Param(staffId, staffName, msisdn);
        }

    }
}
