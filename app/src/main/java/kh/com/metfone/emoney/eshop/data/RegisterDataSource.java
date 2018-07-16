package kh.com.metfone.emoney.eshop.data;

import kh.com.metfone.emoney.eshop.data.models.BaseResult;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/12/2018.
 */

public interface RegisterDataSource {
    Flowable<BaseResult> register(String username, String private_code, String password, String category_id, String shopname, String address);
}
