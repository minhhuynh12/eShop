package kh.com.metfone.emoney.eshop.data;

import kh.com.metfone.emoney.eshop.data.models.BaseResult;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/12/2018.
 */

public interface ForgotPassChangeDataSource {
    Flowable<BaseResult> forgotPassword(String phoneNumber, String privateCode, String password);
}
