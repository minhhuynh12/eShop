package kh.com.metfone.emoney.eshop.data.domain;

import kh.com.metfone.emoney.eshop.data.GetAllReportRepository;
import kh.com.metfone.emoney.eshop.data.executor.PostExecutionThread;
import kh.com.metfone.emoney.eshop.data.executor.ThreadExecutor;
import kh.com.metfone.emoney.eshop.data.models.Report;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/20/2018.
 */

public class GetAllReportUseCase extends FlowableUseCase<Report, GetAllReportUseCase.Params> {
    private final GetAllReportRepository getAllReportRepository;

    @Inject
    GetAllReportUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, GetAllReportRepository getAllReportRepository) {
        super(threadExecutor, postExecutionThread);
        this.getAllReportRepository = getAllReportRepository;
    }

    @Override
    Flowable<Report> buildUseCaseObservable(Params params) {
        return getAllReportRepository.getAllReport(params.shopId, params.fromDate, params.toDate);
    }

    public static class Params {
        private String shopId;
        private String fromDate;
        private String toDate;

        public Params(String shopId, String fromDate, String toDate) {
            this.shopId = shopId;
            this.fromDate = fromDate;
            this.toDate = toDate;
        }

        public static Params forParams(String shopId, String fromDate, String toDate) {
            return new Params(shopId, fromDate, toDate);
        }
    }
}
