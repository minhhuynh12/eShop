package kh.com.metfone.emoney.eshop.data.domain;

import kh.com.metfone.emoney.eshop.data.StaffResetPasswordRepository;
import kh.com.metfone.emoney.eshop.data.executor.PostExecutionThread;
import kh.com.metfone.emoney.eshop.data.executor.ThreadExecutor;
import kh.com.metfone.emoney.eshop.data.models.StaffResetPassResponse;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/15/2018.
 */

public class StaffResetPasswordUseCase extends FlowableUseCase<StaffResetPassResponse, StaffResetPasswordUseCase.Param> {
    private final StaffResetPasswordRepository resetPasswordRepository;

    @Inject
    StaffResetPasswordUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, StaffResetPasswordRepository resetPasswordRepository) {
        super(threadExecutor, postExecutionThread);
        this.resetPasswordRepository = resetPasswordRepository;
    }

    @Override
    Flowable<StaffResetPassResponse> buildUseCaseObservable(Param param) {
        return resetPasswordRepository.resetPasswordStaff(param.receiptId);
    }

    public static class Param {
        private int receiptId;
        public Param(int staffId) {
            this.receiptId = staffId;
        }

        public static Param forParam(int staffId) {
            return new Param(staffId);
        }
    }
}
