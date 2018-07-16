package kh.com.metfone.emoney.eshop.data.domain;

import kh.com.metfone.emoney.eshop.data.UserRepository;
import kh.com.metfone.emoney.eshop.data.executor.PostExecutionThread;
import kh.com.metfone.emoney.eshop.data.executor.ThreadExecutor;
import kh.com.metfone.emoney.eshop.data.models.UserInformation;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/9/2018.
 */

public class LoginUseCase extends FlowableUseCase<UserInformation, LoginUseCase.Params> {
    private final UserRepository userRepository;

    @Inject
    LoginUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
                 UserRepository userRepository) {
        super(threadExecutor, postExecutionThread);
        this.userRepository = userRepository;
    }

    @Override
    Flowable<UserInformation> buildUseCaseObservable(Params params) {
        return userRepository.login(params.userName, params.password, params.fireBaseToken);
    }

    public void saveUserInfo(UserInformation userInformation) {
        userRepository.saveUserInfo(userInformation);
    }

    public static final class Params {
        private String userName;
        private String password;
        private String fireBaseToken;

        public Params(String userName, String password, String fireBaseToken) {
            this.userName = userName;
            this.password = password;
            this.fireBaseToken = fireBaseToken;
        }

        public static Params forParams(String userName, String password, String fireBaseToken) {
            return new Params(userName, password, fireBaseToken);
        }
    }
}
