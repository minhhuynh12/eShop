package kh.com.metfone.emoney.eshop.data.domain;

import kh.com.metfone.emoney.eshop.data.USSDRepository;
import kh.com.metfone.emoney.eshop.data.executor.PostExecutionThread;
import kh.com.metfone.emoney.eshop.data.executor.ThreadExecutor;
import kh.com.metfone.emoney.eshop.data.models.UssdResponse;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/15/2018.
 */

public class USSDUseCase extends FlowableUseCase<UssdResponse, USSDUseCase.Param> {
    private final USSDRepository ussdRepository;

    @Inject
    USSDUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, USSDRepository checkStatusPaymentRepository) {
        super(threadExecutor, postExecutionThread);
        this.ussdRepository = checkStatusPaymentRepository;
    }

    @Override
    Flowable<UssdResponse> buildUseCaseObservable(Param param) {
        return ussdRepository.confirmByPhone(param.receiptId);
    }

    public static class Param {
        private int receiptId;
        public Param(int receiptId) {
            this.receiptId = receiptId;
        }

        public static Param forParam(int receiptId) {
            return new Param(receiptId);
        }
    }
}
