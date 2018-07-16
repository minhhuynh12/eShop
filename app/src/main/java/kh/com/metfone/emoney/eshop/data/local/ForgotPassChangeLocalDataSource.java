package kh.com.metfone.emoney.eshop.data.local;

import kh.com.metfone.emoney.eshop.data.ForgotPassChangeDataSource;
import kh.com.metfone.emoney.eshop.data.models.BaseResult;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/12/2018.
 */

public class ForgotPassChangeLocalDataSource implements ForgotPassChangeDataSource {
    @Override
    public Flowable<BaseResult> forgotPassword(String phoneNumber, String privateCode, String password) {
        return null;
    }
}
