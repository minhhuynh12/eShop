package kh.com.metfone.emoney.eshop.data;

import kh.com.metfone.emoney.eshop.data.models.BaseResult;
import kh.com.metfone.emoney.eshop.injection.Local;
import kh.com.metfone.emoney.eshop.injection.Remote;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/12/2018.
 */

public class StaffNewRepository implements StaffNewDataSource {
    private final StaffNewDataSource localStaffNewDataSource;
    private final StaffNewDataSource remoteStaffNewDataSource;

    @Inject
    public StaffNewRepository(@Local StaffNewDataSource localStaffNewDataSource,
                              @Remote StaffNewDataSource remoteStaffNewDataSource) {
        this.localStaffNewDataSource = localStaffNewDataSource;
        this.remoteStaffNewDataSource = remoteStaffNewDataSource;
    }

    @Override
    public Flowable<BaseResult> newStaff(String staffCode, String staffName, String msisdn ) {
        return remoteStaffNewDataSource.newStaff(staffCode, staffName, msisdn);
//        return null;
    }
}
