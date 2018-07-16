package kh.com.metfone.emoney.eshop.data;

import kh.com.metfone.emoney.eshop.data.models.BaseResult;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/12/2018.
 */

public interface StaffUpdateDataSource {
    Flowable<BaseResult> updateStaff(String staffId, String staffName, String msisdn);
}
