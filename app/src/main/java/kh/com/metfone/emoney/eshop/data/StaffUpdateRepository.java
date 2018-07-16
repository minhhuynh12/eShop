package kh.com.metfone.emoney.eshop.data;

import kh.com.metfone.emoney.eshop.data.models.BaseResult;
import kh.com.metfone.emoney.eshop.injection.Local;
import kh.com.metfone.emoney.eshop.injection.Remote;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/12/2018.
 */

public class StaffUpdateRepository implements StaffUpdateDataSource {
    private final StaffUpdateDataSource localStaffUpdateDataSource;
    private final StaffUpdateDataSource remoteStaffUpdateDataSource;

    @Inject
    public StaffUpdateRepository(@Local StaffUpdateDataSource localStaffUpdateDataSource,
                                 @Remote StaffUpdateDataSource remoteStaffUpdateDataSource) {
        this.localStaffUpdateDataSource = localStaffUpdateDataSource;
        this.remoteStaffUpdateDataSource = remoteStaffUpdateDataSource;
    }

    @Override
    public Flowable<BaseResult> updateStaff(String staffId, String staffName, String msisdn) {
        return remoteStaffUpdateDataSource.updateStaff(staffId, staffName, msisdn);
//        return null;
    }
}
