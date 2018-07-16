package kh.com.metfone.emoney.eshop.data;

import kh.com.metfone.emoney.eshop.data.models.UssdResponse;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/14/2018.
 */

public interface USSDDataSource {
    Flowable<UssdResponse> confirmByPhone(int receiptId);
}
