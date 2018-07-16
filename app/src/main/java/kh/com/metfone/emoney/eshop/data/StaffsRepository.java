package kh.com.metfone.emoney.eshop.data;

import kh.com.metfone.emoney.eshop.data.models.StaffsResponse;
import kh.com.metfone.emoney.eshop.injection.Local;
import kh.com.metfone.emoney.eshop.injection.Remote;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/12/2018.
 */

public class StaffsRepository implements StaffsDataSource {
    private final StaffsDataSource localStaffsDataSource;
    private final StaffsDataSource remoteStaffsDataSource;

    @Inject
    public StaffsRepository(@Local StaffsDataSource localStaffsDataSource,
                            @Remote StaffsDataSource remoteStaffsDataSource) {
        this.localStaffsDataSource = localStaffsDataSource;
        this.remoteStaffsDataSource = remoteStaffsDataSource;
    }


    @Override
    public Flowable<StaffsResponse> getStaffs() {
        return remoteStaffsDataSource.getStaffs();
    }
}
