package kh.com.metfone.emoney.eshop.data;

import kh.com.metfone.emoney.eshop.data.models.Receipt;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/14/2018.
 */

public interface CheckStatusPaymentDataSource {
    Flowable<Receipt> checkStatus(int receiptId);
}
