package kh.com.metfone.emoney.eshop.data;

import kh.com.metfone.emoney.eshop.data.models.Report;
import kh.com.metfone.emoney.eshop.injection.Local;
import kh.com.metfone.emoney.eshop.injection.Remote;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/15/2018.
 */

public class GetAllReportRepository implements GetAllReportDataSource {

    private final GetAllReportDataSource getAllReportRemoteDataSource;
    private final GetAllReportDataSource getAllReportLocalDataSource;

    @Inject
    GetAllReportRepository(@Local GetAllReportDataSource getAllReportLocalDataSource,
                           @Remote GetAllReportDataSource getAllReportRemoteDataSource) {
        this.getAllReportLocalDataSource = getAllReportLocalDataSource;
        this.getAllReportRemoteDataSource = getAllReportRemoteDataSource;
    }

    @Override
    public Flowable<Report> getAllReport(String shopId, String fromDate, String toDate) {
        return getAllReportRemoteDataSource.getAllReport(shopId, fromDate, toDate);
    }
}
