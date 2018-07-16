package kh.com.metfone.emoney.eshop.data.local;

import kh.com.metfone.emoney.eshop.data.StaffUpdateDataSource;
import kh.com.metfone.emoney.eshop.data.models.BaseResult;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/12/2018.
 */

public class StaffUpdateLocalDataSource implements StaffUpdateDataSource {

    @Override
    public Flowable<BaseResult> updateStaff(String staffId, String staffName, String msisdn) {
        return null;
    }
}
