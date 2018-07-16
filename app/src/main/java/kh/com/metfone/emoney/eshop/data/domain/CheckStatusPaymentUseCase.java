package kh.com.metfone.emoney.eshop.data.domain;

import kh.com.metfone.emoney.eshop.data.CheckStatusPaymentRepository;
import kh.com.metfone.emoney.eshop.data.executor.PostExecutionThread;
import kh.com.metfone.emoney.eshop.data.executor.ThreadExecutor;
import kh.com.metfone.emoney.eshop.data.models.Receipt;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/15/2018.
 */

public class CheckStatusPaymentUseCase extends FlowableUseCase<Receipt, CheckStatusPaymentUseCase.Param> {
    private final CheckStatusPaymentRepository checkStatusPaymentRepository;

    @Inject
    CheckStatusPaymentUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, CheckStatusPaymentRepository checkStatusPaymentRepository) {
        super(threadExecutor, postExecutionThread);
        this.checkStatusPaymentRepository = checkStatusPaymentRepository;
    }

    @Override
    Flowable<Receipt> buildUseCaseObservable(Param param) {
        return checkStatusPaymentRepository.checkStatus(param.receiptId);
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
