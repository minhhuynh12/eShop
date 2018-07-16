package kh.com.metfone.emoney.eshop.data;

import kh.com.metfone.emoney.eshop.data.models.BaseResult;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/15/2018.
 */

public interface ChangePasswordDataSource {
    Flowable<BaseResult> changePassword(String oldPassword, String newPassword);
}
