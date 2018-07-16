package kh.com.metfone.emoney.eshop.data.local;

import kh.com.metfone.emoney.eshop.data.CheckStatusPaymentDataSource;
import kh.com.metfone.emoney.eshop.data.models.Receipt;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/15/2018.
 */

public class CheckStatusPaymentLocalDataSource implements CheckStatusPaymentDataSource {
    @Override
    public Flowable<Receipt> checkStatus(int receiptId) {
        return null;
    }
}
