package kh.com.metfone.emoney.eshop.data.remote;

import kh.com.metfone.emoney.eshop.consts.Const;
import kh.com.metfone.emoney.eshop.data.StaffNewDataSource;
import kh.com.metfone.emoney.eshop.data.models.BaseResult;
import kh.com.metfone.emoney.eshop.utils.SharePreferenceHelper;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/12/2018.
 */
@Singleton
public class StaffNewRemoteDataSource implements StaffNewDataSource {
    private final SharePreferenceHelper sharePreferenceHelper;
    private final RetrofitService retrofitService;

    @Inject
    public StaffNewRemoteDataSource(RetrofitService retrofitService, SharePreferenceHelper sharePreferenceHelper) {
        this.retrofitService = retrofitService;
        this.sharePreferenceHelper = sharePreferenceHelper;
    }

    @Override
    public Flowable<BaseResult> newStaff(String staffCode, String staffName, String msisdn){
        Map<String, String> body = new HashMap<>();
        body.put(Const.KEY_STAFF_CODE, staffCode);
        body.put(Const.KEY_STAFF_NAME, staffName);
        body.put(Const.KEY_STAFF_MSISDN, msisdn);
        String accessToken = "Basic " + sharePreferenceHelper.getAccessToken();
        String username = sharePreferenceHelper.getUserName();
        return retrofitService.newStaffs(username, accessToken, sharePreferenceHelper.getLanguage(), body);
    }

}
