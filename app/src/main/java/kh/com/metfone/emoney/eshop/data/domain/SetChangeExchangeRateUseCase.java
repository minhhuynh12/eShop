package kh.com.metfone.emoney.eshop.data.domain;

import kh.com.metfone.emoney.eshop.data.GetCommonConfigInfoRepository;
import kh.com.metfone.emoney.eshop.data.executor.PostExecutionThread;
import kh.com.metfone.emoney.eshop.data.executor.ThreadExecutor;
import kh.com.metfone.emoney.eshop.data.models.BaseResult;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/16/2018.
 */

public class SetChangeExchangeRateUseCase extends FlowableUseCase<BaseResult, SetChangeExchangeRateUseCase.Param> {
    private final GetCommonConfigInfoRepository getCommonConfigInfoRepository;

    @Inject
    SetChangeExchangeRateUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, GetCommonConfigInfoRepository getCommonConfigInfoRepository) {
        super(threadExecutor, postExecutionThread);
        this.getCommonConfigInfoRepository = getCommonConfigInfoRepository;
    }

    @Override
    Flowable<BaseResult> buildUseCaseObservable(Param param) {
        return getCommonConfigInfoRepository.changeExchangeRate(param.khrMoney);
    }

    public static class Param {
        private String khrMoney;

        public Param(String khrMoney) {
            this.khrMoney = khrMoney;
        }

        public static Param forParam(String khrMoney) {
            return new Param(khrMoney);
        }
    }
}
