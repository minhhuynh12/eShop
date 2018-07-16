package kh.com.metfone.emoney.eshop.data;

import kh.com.metfone.emoney.eshop.data.models.ReceiptsResponse;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/12/2018.
 */

public interface ReceiptsDataSource {
    Flowable<ReceiptsResponse> receipts(String shopId, String fromDate, String toDate);
}
