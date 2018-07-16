package kh.com.metfone.emoney.eshop.data;

import kh.com.metfone.emoney.eshop.data.models.BaseResult;
import kh.com.metfone.emoney.eshop.injection.Local;
import kh.com.metfone.emoney.eshop.injection.Remote;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/15/2018.
 */

public class ChangePasswordRepository implements ChangePasswordDataSource {

    private final ChangePasswordDataSource changePasswordRemoteDataSource;
    private final ChangePasswordDataSource changePasswordLocalDataSource;

    @Inject
ChangePasswordRepository(@Local ChangePasswordDataSource changePasswordLocalDataSource,
                         @Remote ChangePasswordDataSource changePasswordRemoteDataSource) {
    this.changePasswordLocalDataSource = changePasswordLocalDataSource;
    this.changePasswordRemoteDataSource = changePasswordRemoteDataSource;
}
    @Override
    public Flowable<BaseResult> changePassword(String oldPassword, String newPassword) {
        return changePasswordRemoteDataSource.changePassword(oldPassword, newPassword);
    }
}
