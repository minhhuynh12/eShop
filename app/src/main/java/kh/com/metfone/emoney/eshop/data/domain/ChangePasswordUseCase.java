package kh.com.metfone.emoney.eshop.data.domain;

import kh.com.metfone.emoney.eshop.data.ChangePasswordRepository;
import kh.com.metfone.emoney.eshop.data.executor.PostExecutionThread;
import kh.com.metfone.emoney.eshop.data.executor.ThreadExecutor;
import kh.com.metfone.emoney.eshop.data.models.BaseResult;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/15/2018.
 */

public class ChangePasswordUseCase extends FlowableUseCase<BaseResult, ChangePasswordUseCase.Params> {
    private final ChangePasswordRepository changePasswordRepository;

    @Inject
    ChangePasswordUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, ChangePasswordRepository changePasswordRepository) {
        super(threadExecutor, postExecutionThread);
        this.changePasswordRepository = changePasswordRepository;
    }

    @Override
    Flowable<BaseResult> buildUseCaseObservable(Params params) {
        return changePasswordRepository.changePassword(params.oldPassword, params.newPassword);
    }

    public static class Params {
        private String oldPassword;
        private String newPassword;
        public Params(String oldPassword, String newPassword) {
            this.oldPassword = oldPassword;
            this.newPassword = newPassword;
        }
        public static Params forParams(String oldPassword, String newPassword) {
            return new Params(oldPassword, newPassword);
        }
    }
}
