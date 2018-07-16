package kh.com.metfone.emoney.eshop.data.local;

import kh.com.metfone.emoney.eshop.data.StaffResetPasswordDataSource;
import kh.com.metfone.emoney.eshop.data.models.StaffResetPassResponse;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/15/2018.
 */

public class StaffResetPasswordLocalDataSource implements StaffResetPasswordDataSource {

    @Override
    public Flowable<StaffResetPassResponse> resetPasswordStaff(int staffId) {
        return null;
    }
}
