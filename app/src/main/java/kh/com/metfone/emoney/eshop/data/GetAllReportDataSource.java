package kh.com.metfone.emoney.eshop.data;

import kh.com.metfone.emoney.eshop.data.models.Report;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/20/2018.
 */

public interface GetAllReportDataSource {

    Flowable<Report> getAllReport(String shopId, String fromDate, String toDate);

}
