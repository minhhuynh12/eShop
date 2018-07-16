package kh.com.metfone.emoney.eshop.data.domain;

import kh.com.metfone.emoney.eshop.data.UserRepository;
import kh.com.metfone.emoney.eshop.data.executor.PostExecutionThread;
import kh.com.metfone.emoney.eshop.data.executor.ThreadExecutor;
import kh.com.metfone.emoney.eshop.data.models.StaffInShop;
import kh.com.metfone.emoney.eshop.data.models.UserInformation;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/12/2018.
 */

public class GetUserInforUseCase extends FlowableUseCase<UserInformation, GetUserInforUseCase.Params> {
    private final UserRepository userRepository;

    @Inject
    GetUserInforUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
                        UserRepository userRepository) {
        super(threadExecutor, postExecutionThread);
        this.userRepository = userRepository;
    }

    @Override
    Flowable<UserInformation> buildUseCaseObservable(Params params) {
        return userRepository.getUserInformation();
    }

    public void saveUserInfo(UserInformation userInformation) {
        userRepository.saveUserInfo(userInformation);
    }

    public void saveStaff(StaffInShop staffInShop) {
        userRepository.saveStaff(staffInShop);
    }

    public class Params {
    }
}
