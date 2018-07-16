package kh.com.metfone.emoney.eshop.data.local;

import kh.com.metfone.emoney.eshop.data.GetCodeDataSource;
import kh.com.metfone.emoney.eshop.data.models.BaseResult;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/12/2018.
 */

public class GetCodeForgotPassLocalDataSource implements GetCodeDataSource {
    @Override
    public Flowable<BaseResult> getCode(String phoneNumber) {
        return null;
    }
}
