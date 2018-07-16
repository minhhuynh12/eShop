package kh.com.metfone.emoney.eshop.data.remote;

import kh.com.metfone.emoney.eshop.consts.Const;
import kh.com.metfone.emoney.eshop.data.GetCommonConfigInfoDataSource;
import kh.com.metfone.emoney.eshop.data.models.BaseResult;
import kh.com.metfone.emoney.eshop.data.models.CommonConfigInfo;
import kh.com.metfone.emoney.eshop.utils.SharePreferenceHelper;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/9/2018.
 */
@Singleton
public class GetCommonConfigInfoRemoteDataSource implements GetCommonConfigInfoDataSource {
    private final RetrofitService retrofitService;
    private final SharePreferenceHelper sharePreferenceHelper;

    @Inject
    public GetCommonConfigInfoRemoteDataSource(RetrofitService retrofitService, SharePreferenceHelper sharePreferenceHelper) {
        this.retrofitService = retrofitService;
        this.sharePreferenceHelper = sharePreferenceHelper;
    }


    @Override
    public Flowable<CommonConfigInfo> getCommonConfigInformation() {
//        Map<String, String> body = new HashMap<>();
        return retrofitService.getCommonInformation();
    }

    @Override
    public Flowable<CommonConfigInfo> getCommonConfigInfo() {
        return null;
    }

    @Override
    public Flowable<BaseResult> changeExchangeRate(String khrMoney) {
        String accessToken = "Basic " + sharePreferenceHelper.getAccessToken();
        String username = sharePreferenceHelper.getUserName();
        String language = sharePreferenceHelper.getLanguage();
        Map<String, String> body = new HashMap<>();
        body.put(Const.KEY_STTYPE, "1");
        body.put(Const.KEY_STKEY, Const.CURRENCY_EXCHANGE_RATE_USD_KHR);
        body.put(Const.KEY_STVALUE, khrMoney);
        return retrofitService.changeExchangeRate(username, accessToken, language, body);
    }

    @Override
    public void saveCommonConfigInfo(CommonConfigInfo commonConfigInfo) {

    }

    @Override
    public void clearCommonConfigInfo() {

    }
}
