package kh.com.metfone.emoney.eshop.data.domain;

import kh.com.metfone.emoney.eshop.data.UserRepository;
import kh.com.metfone.emoney.eshop.data.executor.PostExecutionThread;
import kh.com.metfone.emoney.eshop.data.executor.ThreadExecutor;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/12/2018.
 */

public class GetUserStatusUseCase extends FlowableUseCase<Boolean, GetUserStatusUseCase.Params> {
    private final UserRepository userRepository;

    @Inject
    GetUserStatusUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
                         UserRepository userRepository) {
        super(threadExecutor, postExecutionThread);
        this.userRepository = userRepository;
    }

    @Override
    Flowable<Boolean> buildUseCaseObservable(Params params) {
        return userRepository.isLoggedIn();
    }

    public class Params {
    }
}
