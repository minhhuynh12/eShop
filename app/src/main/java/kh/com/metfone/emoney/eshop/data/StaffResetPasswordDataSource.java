package kh.com.metfone.emoney.eshop.data;

import kh.com.metfone.emoney.eshop.data.models.StaffResetPassResponse;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/14/2018.
 */

public interface StaffResetPasswordDataSource {
    Flowable<StaffResetPassResponse> resetPasswordStaff(int staffId);
}
