package kh.com.metfone.emoney.eshop.data;

import kh.com.metfone.emoney.eshop.data.models.Receipt;
import kh.com.metfone.emoney.eshop.injection.Local;
import kh.com.metfone.emoney.eshop.injection.Remote;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/15/2018.
 */

public class CheckStatusPaymentRepository implements CheckStatusPaymentDataSource {
    private final CheckStatusPaymentDataSource checkStatusPaymentRemoteDataSource;
    private final CheckStatusPaymentDataSource checkStatusPaymentLocalDataSource;

    @Inject
    CheckStatusPaymentRepository(@Local CheckStatusPaymentDataSource checkStatusPaymentLocalDataSource,
                                 @Remote CheckStatusPaymentDataSource checkStatusPaymentRemoteDataSource) {
        this.checkStatusPaymentLocalDataSource = checkStatusPaymentLocalDataSource;
        this.checkStatusPaymentRemoteDataSource = checkStatusPaymentRemoteDataSource;
    }
    @Override
    public Flowable<Receipt> checkStatus(int receiptId) {
        return checkStatusPaymentRemoteDataSource.checkStatus(receiptId);
    }
}
