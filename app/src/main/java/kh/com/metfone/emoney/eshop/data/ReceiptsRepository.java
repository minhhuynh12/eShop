package kh.com.metfone.emoney.eshop.data;

import kh.com.metfone.emoney.eshop.data.models.ReceiptsResponse;
import kh.com.metfone.emoney.eshop.injection.Local;
import kh.com.metfone.emoney.eshop.injection.Remote;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/12/2018.
 */

public class ReceiptsRepository implements ReceiptsDataSource {
    private final ReceiptsDataSource localReceiptsDataSource;
    private final ReceiptsDataSource remoteReceiptsDataSource;

    @Inject
    public ReceiptsRepository(@Local ReceiptsDataSource localReceiptsDataSource,
                              @Remote ReceiptsDataSource remoteReceiptsDataSource) {
        this.localReceiptsDataSource = localReceiptsDataSource;
        this.remoteReceiptsDataSource = remoteReceiptsDataSource;
    }


    @Override
    public Flowable<ReceiptsResponse> receipts(String shopId, String fromDate, String toDate) {
        return remoteReceiptsDataSource.receipts(shopId, fromDate, toDate);
    }
}
