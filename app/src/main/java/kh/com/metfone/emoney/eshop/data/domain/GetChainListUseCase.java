package kh.com.metfone.emoney.eshop.data.domain;

import kh.com.metfone.emoney.eshop.data.ShopRepository;
import kh.com.metfone.emoney.eshop.data.executor.PostExecutionThread;
import kh.com.metfone.emoney.eshop.data.executor.ThreadExecutor;
import kh.com.metfone.emoney.eshop.data.models.ChainList;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/15/2018.
 */

public class GetChainListUseCase extends FlowableUseCase<ChainList, GetChainListUseCase.Param> {
    private final ShopRepository shopRepository;

    @Inject
    GetChainListUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, ShopRepository shopRepository) {
        super(threadExecutor, postExecutionThread);
        this.shopRepository = shopRepository;
    }

    @Override
    Flowable<ChainList> buildUseCaseObservable(Param param) {
        return shopRepository.getChainList();
    }

    public static class Param {
    }
}
