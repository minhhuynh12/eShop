package kh.com.metfone.emoney.eshop.data.local;

import kh.com.metfone.emoney.eshop.data.StaffsDataSource;
import kh.com.metfone.emoney.eshop.data.models.StaffsResponse;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/12/2018.
 */

public class StaffsLocalDataSource implements StaffsDataSource {

    @Override
    public Flowable<StaffsResponse> getStaffs() {
        return null;
    }
}
