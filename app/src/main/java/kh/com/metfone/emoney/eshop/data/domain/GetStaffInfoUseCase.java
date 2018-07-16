package kh.com.metfone.emoney.eshop.data.domain;

import kh.com.metfone.emoney.eshop.data.UserRepository;
import kh.com.metfone.emoney.eshop.data.executor.PostExecutionThread;
import kh.com.metfone.emoney.eshop.data.executor.ThreadExecutor;
import kh.com.metfone.emoney.eshop.data.models.StaffInShop;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/12/2018.
 */

public class GetStaffInfoUseCase extends FlowableUseCase<StaffInShop, GetStaffInfoUseCase.Params> {
    private final UserRepository userRepository;

    @Inject
    GetStaffInfoUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
                        UserRepository userRepository) {
        super(threadExecutor, postExecutionThread);
        this.userRepository = userRepository;
    }

    @Override
    Flowable<StaffInShop> buildUseCaseObservable(Params params) {
        return userRepository.getStaffInfo(params.staffId);
    }

    public static class Params {
        private String staffId;

        public Params(String staffId) {
            this.staffId = staffId;
        }

        public static Params forParam(String staffId) {
            return new Params(staffId);
        }
    }
}
