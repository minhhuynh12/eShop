package kh.com.metfone.emoney.eshop.data;

import kh.com.metfone.emoney.eshop.data.models.BaseResult;
import kh.com.metfone.emoney.eshop.injection.Local;
import kh.com.metfone.emoney.eshop.injection.Remote;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/12/2018.
 */

public class ForgotPassChangeRepository implements ForgotPassChangeDataSource {
    private final ForgotPassChangeDataSource localForgotPassChangeDataSource;
    private final ForgotPassChangeDataSource remoteForgotPassChangeDataSource;

    @Inject
    public ForgotPassChangeRepository(@Local ForgotPassChangeDataSource localForgotPassChangeDataSource,
                                      @Remote ForgotPassChangeDataSource remoteForgotPassChangeDataSource) {
        this.localForgotPassChangeDataSource = localForgotPassChangeDataSource;
        this.remoteForgotPassChangeDataSource = remoteForgotPassChangeDataSource;
    }
    @Override
    public Flowable<BaseResult> forgotPassword(String phoneNumber, String privateCode, String password) {
        return remoteForgotPassChangeDataSource.forgotPassword(phoneNumber, privateCode, password);
    }
}
