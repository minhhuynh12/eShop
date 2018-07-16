package kh.com.metfone.emoney.eshop.data.remote;

import kh.com.metfone.emoney.eshop.data.GetCodeRegisterDataSource;
import kh.com.metfone.emoney.eshop.data.models.BaseResult;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/12/2018.
 */

@Singleton
public class GetCodeRegisterRemoteDataSource implements GetCodeRegisterDataSource {
    private final RetrofitService retrofitService;

    @Inject
    public GetCodeRegisterRemoteDataSource(RetrofitService retrofitService) {
        this.retrofitService = retrofitService;
    }
    @Override
    public Flowable<BaseResult> getRegisterCode(String phoneNumber) {
        return retrofitService.getRegisterCode(phoneNumber);
    }
}
