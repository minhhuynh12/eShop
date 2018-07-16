package kh.com.metfone.emoney.eshop.data;

import kh.com.metfone.emoney.eshop.data.models.BaseResult;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/12/2018.
 */

public interface StaffNewDataSource {
    Flowable<BaseResult> newStaff(String staffCode, String staffName, String msisdn );
}
