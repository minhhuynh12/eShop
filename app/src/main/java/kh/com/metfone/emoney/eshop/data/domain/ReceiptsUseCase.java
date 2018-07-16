package kh.com.metfone.emoney.eshop.data.domain;

import kh.com.metfone.emoney.eshop.data.ReceiptsRepository;
import kh.com.metfone.emoney.eshop.data.executor.PostExecutionThread;
import kh.com.metfone.emoney.eshop.data.executor.ThreadExecutor;
import kh.com.metfone.emoney.eshop.data.models.ReceiptsResponse;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/12/2018.
 */

public class ReceiptsUseCase extends FlowableUseCase<ReceiptsResponse, ReceiptsUseCase.Params> {

    private final ReceiptsRepository repository;

    @Inject
    ReceiptsUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, ReceiptsRepository repository) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }

    @Override
    Flowable<ReceiptsResponse> buildUseCaseObservable(Params param) {
        return repository.receipts(param.shopId, param.fromDate, param.toDate);
    }


    public static class Params {
        private String shopId;
        private String fromDate;
        private String toDate;

        public Params(String shop_Id, String from_Date, String to_Date) {
            this.shopId = shop_Id;
            this.fromDate = from_Date;
            this.toDate = to_Date;
        }

        public static Params forParam(String shop_Id, String from_Date, String to_Date) {
            return new Params(shop_Id , from_Date, to_Date);
        }

    }
}
