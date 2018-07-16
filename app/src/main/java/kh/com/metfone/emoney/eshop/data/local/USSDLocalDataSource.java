package kh.com.metfone.emoney.eshop.data.local;

import kh.com.metfone.emoney.eshop.data.USSDDataSource;
import kh.com.metfone.emoney.eshop.data.models.UssdResponse;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/15/2018.
 */

public class USSDLocalDataSource implements USSDDataSource {

    @Override
    public Flowable<UssdResponse> confirmByPhone(int receiptId) {
        return null;
    }
}
