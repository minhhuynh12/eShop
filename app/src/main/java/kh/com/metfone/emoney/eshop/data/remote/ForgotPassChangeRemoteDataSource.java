package kh.com.metfone.emoney.eshop.data.remote;

import kh.com.metfone.emoney.eshop.consts.Const;
import kh.com.metfone.emoney.eshop.data.ForgotPassChangeDataSource;
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
public class ForgotPassChangeRemoteDataSource implements ForgotPassChangeDataSource {
    private final RetrofitService retrofitService;
    private final SharePreferenceHelper sharePreferenceHelper;

    @Inject
    public ForgotPassChangeRemoteDataSource(RetrofitService retrofitService,
                                            SharePreferenceHelper sharePreferenceHelper) {
        this.retrofitService = retrofitService;
        this.sharePreferenceHelper = sharePreferenceHelper;

    }

    @Override
    public Flowable<BaseResult> forgotPassword(String phoneNumber, String privateCode, String password) {
        Map<String, String> body = new HashMap<>();
        body.put(Const.KEY_MSISDN, phoneNumber);
        body.put(Const.KEY_PRIVATE_KEY, privateCode);
        body.put(Const.KEY_PASSWORD, password);
        return retrofitService.forgotPassword(sharePreferenceHelper.getLanguage(), body);
    }
}
