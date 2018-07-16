package kh.com.metfone.emoney.eshop.data.local;

import kh.com.metfone.emoney.eshop.data.ReceiptsDataSource;
import kh.com.metfone.emoney.eshop.data.models.ReceiptsResponse;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/12/2018.
 */

public class ReceiptsLocalDataSource implements ReceiptsDataSource {

    @Override
    public Flowable<ReceiptsResponse> receipts(String shopId, String fromDate, String toDate) {
        return null;
    }
}
