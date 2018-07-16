package kh.com.metfone.emoney.eshop.data.domain;

import kh.com.metfone.emoney.eshop.data.UserRepository;
import kh.com.metfone.emoney.eshop.data.executor.PostExecutionThread;
import kh.com.metfone.emoney.eshop.data.executor.ThreadExecutor;
import kh.com.metfone.emoney.eshop.data.models.ShopConfig;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/27/2018.
 */

public class GetShopConfigUseCase extends FlowableUseCase<ShopConfig, GetShopConfigUseCase.Param> {

    private final UserRepository userRepository;

    @Inject
    GetShopConfigUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, UserRepository userRepository) {
        super(threadExecutor, postExecutionThread);
        this.userRepository = userRepository;
    }

    @Override
    Flowable<ShopConfig> buildUseCaseObservable(Param param) {
        return userRepository.getShopConfig();
    }

    public class Param {
    }
}
