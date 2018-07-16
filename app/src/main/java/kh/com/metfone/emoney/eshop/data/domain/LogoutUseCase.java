package kh.com.metfone.emoney.eshop.data.domain;

import kh.com.metfone.emoney.eshop.data.UserRepository;
import kh.com.metfone.emoney.eshop.data.executor.PostExecutionThread;
import kh.com.metfone.emoney.eshop.data.executor.ThreadExecutor;
import kh.com.metfone.emoney.eshop.data.models.BaseResult;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/15/2018.
 */

public class LogoutUseCase extends FlowableUseCase<BaseResult, LogoutUseCase.Param> {
    private final UserRepository userRepository;

    @Inject
    LogoutUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, UserRepository userRepository) {
        super(threadExecutor, postExecutionThread);
        this.userRepository = userRepository;
    }
    public void clearUserInfor() {
        userRepository.clearUserInfo();
    }

    @Override
    Flowable<BaseResult> buildUseCaseObservable(Param param) {
        return userRepository.logout();
    }

    public class Param {
    }
}
