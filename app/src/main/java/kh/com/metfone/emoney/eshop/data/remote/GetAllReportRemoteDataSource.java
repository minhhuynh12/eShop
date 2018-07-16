package kh.com.metfone.emoney.eshop.data.remote;

import kh.com.metfone.emoney.eshop.consts.Const;
import kh.com.metfone.emoney.eshop.data.GetAllReportDataSource;
import kh.com.metfone.emoney.eshop.data.models.Report;
import kh.com.metfone.emoney.eshop.utils.SharePreferenceHelper;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/20/2018.
 */
@Singleton
public class GetAllReportRemoteDataSource implements GetAllReportDataSource {
    private final SharePreferenceHelper sharePreferenceHelper;
    private final RetrofitService retrofitService;

    @Inject
    public GetAllReportRemoteDataSource(RetrofitService retrofitService, SharePreferenceHelper sharePreferenceHelper) {
        this.retrofitService = retrofitService;
        this.sharePreferenceHelper = sharePreferenceHelper;
    }

    @Override
    public Flowable<Report> getAllReport(String shopId, String fromDate, String toDate) {
        Map<String, String> body = new HashMap<>();
        body.put(Const.KEY_SHOP_ID, shopId);
        body.put(Const.KEY_FROM_DATE, fromDate);
        body.put(Const.KEY_TO_DATE, toDate);
        String accessToken = "Basic " + sharePreferenceHelper.getAccessToken();
        return retrofitService.getAllReport(sharePreferenceHelper.getUserName(), accessToken, sharePreferenceHelper.getLanguage(), body);
    }
}
