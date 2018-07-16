package kh.com.metfone.emoney.eshop.data.local;

import kh.com.metfone.emoney.eshop.data.StaffNewDataSource;
import kh.com.metfone.emoney.eshop.data.models.BaseResult;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/12/2018.
 */

public class StaffNewLocalDataSource implements StaffNewDataSource {

    @Override
    public Flowable<BaseResult> newStaff(String staffCode, String staffName, String msisdn) {
        return null;
    }
}
