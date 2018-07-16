package kh.com.metfone.emoney.eshop.data.local;

import kh.com.metfone.emoney.eshop.data.GetAllReportDataSource;
import kh.com.metfone.emoney.eshop.data.models.Report;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/20/2018.
 */

public class GetAllReportLocalDataSource implements GetAllReportDataSource {
    @Override
    public Flowable<Report> getAllReport(String shopId, String fromDate, String toDate) {
        return null;
    }
}
