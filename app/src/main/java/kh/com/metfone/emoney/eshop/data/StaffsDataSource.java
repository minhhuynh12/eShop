package kh.com.metfone.emoney.eshop.data;

import kh.com.metfone.emoney.eshop.data.models.StaffsResponse;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/12/2018.
 */

public interface StaffsDataSource {
    Flowable<StaffsResponse> getStaffs();
}
