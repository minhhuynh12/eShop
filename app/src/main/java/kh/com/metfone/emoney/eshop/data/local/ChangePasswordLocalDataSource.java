package kh.com.metfone.emoney.eshop.data.local;

import kh.com.metfone.emoney.eshop.data.ChangePasswordDataSource;
import kh.com.metfone.emoney.eshop.data.models.BaseResult;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/15/2018.
 */

public class ChangePasswordLocalDataSource implements ChangePasswordDataSource {

    @Override
    public Flowable<BaseResult> changePassword(String oldPassword, String newPassword) {
        throw new UnsupportedOperationException();
    }
}
