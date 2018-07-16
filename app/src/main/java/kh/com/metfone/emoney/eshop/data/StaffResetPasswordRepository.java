package kh.com.metfone.emoney.eshop.data;

import kh.com.metfone.emoney.eshop.data.models.StaffResetPassResponse;
import kh.com.metfone.emoney.eshop.injection.Local;
import kh.com.metfone.emoney.eshop.injection.Remote;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/15/2018.
 */

public class StaffResetPasswordRepository implements StaffResetPasswordDataSource {
    private final StaffResetPasswordDataSource staffResetPasswordRemoteDataSource;
    private final StaffResetPasswordDataSource staffResetPasswordLocalDataSource;

    @Inject
    StaffResetPasswordRepository(@Local StaffResetPasswordDataSource staffResetPasswordLocalDataSource,
                                 @Remote StaffResetPasswordDataSource staffResetPasswordRemoteDataSource) {
        this.staffResetPasswordLocalDataSource = staffResetPasswordLocalDataSource;
        this.staffResetPasswordRemoteDataSource = staffResetPasswordRemoteDataSource;
    }
    @Override
    public Flowable<StaffResetPassResponse> resetPasswordStaff(int staffId) {
        return staffResetPasswordRemoteDataSource.resetPasswordStaff(staffId);
    }
}
