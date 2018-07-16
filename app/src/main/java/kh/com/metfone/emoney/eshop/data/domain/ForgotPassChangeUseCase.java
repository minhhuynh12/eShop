package kh.com.metfone.emoney.eshop.data.domain;

import kh.com.metfone.emoney.eshop.data.ForgotPassChangeRepository;
import kh.com.metfone.emoney.eshop.data.executor.PostExecutionThread;
import kh.com.metfone.emoney.eshop.data.executor.ThreadExecutor;
import kh.com.metfone.emoney.eshop.data.models.BaseResult;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/12/2018.
 */

public class ForgotPassChangeUseCase extends FlowableUseCase<BaseResult, ForgotPassChangeUseCase.Params> {


    private final ForgotPassChangeRepository forgotPassChangeRepository;

    @Inject
    public ForgotPassChangeUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, ForgotPassChangeRepository forgotPassChangeRepository) {
        super(threadExecutor, postExecutionThread);
        this.forgotPassChangeRepository = forgotPassChangeRepository;
    }

    @Override
    Flowable<BaseResult> buildUseCaseObservable(Params params) {
        return forgotPassChangeRepository.forgotPassword(params.phoneNumber, params.privateCode, params.password);
    }

    public static class Params {
        private String phoneNumber;
        private String privateCode;
        private String password;
        public Params(String phoneNumber, String privateCode, String password) {
            this.phoneNumber = phoneNumber;
            this.privateCode = privateCode;
            this.password = password;
        }

        public static Params forParams(String phoneNumber, String privateCode, String password) {
            return new Params(phoneNumber, privateCode, password);
        }
    }
}
