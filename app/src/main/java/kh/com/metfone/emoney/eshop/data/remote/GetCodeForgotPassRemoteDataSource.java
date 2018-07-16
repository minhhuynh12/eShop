package kh.com.metfone.emoney.eshop.data.remote;

import kh.com.metfone.emoney.eshop.data.GetCodeDataSource;
import kh.com.metfone.emoney.eshop.data.models.BaseResult;
import kh.com.metfone.emoney.eshop.utils.SharePreferenceHelper;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/12/2018.
 */

@Singleton
public class GetCodeForgotPassRemoteDataSource implements GetCodeDataSource {
    private final RetrofitService retrofitService;
    private final SharePreferenceHelper sharePreferenceHelper;

    @Inject
    public GetCodeForgotPassRemoteDataSource(RetrofitService retrofitService,
                                             SharePreferenceHelper sharePreferenceHelper) {
        this.retrofitService = retrofitService;
        this.sharePreferenceHelper = sharePreferenceHelper;
    }
    @Override
    public Flowable<BaseResult> getCode(String phoneNumber) {
        return retrofitService.getCodeForForgotPass(sharePreferenceHelper.getLanguage(), phoneNumber);
    }
}
