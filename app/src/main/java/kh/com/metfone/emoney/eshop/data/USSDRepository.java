package kh.com.metfone.emoney.eshop.data;

import kh.com.metfone.emoney.eshop.data.models.UssdResponse;
import kh.com.metfone.emoney.eshop.injection.Local;
import kh.com.metfone.emoney.eshop.injection.Remote;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/15/2018.
 */

public class USSDRepository implements USSDDataSource {
    private final USSDDataSource ussdRemoteDataSource;
    private final USSDDataSource ussdLocalDataSource;

    @Inject
    USSDRepository(@Local USSDDataSource ussdLocalDataSource,
                   @Remote USSDDataSource ussdRemoteDataSource) {
        this.ussdLocalDataSource = ussdLocalDataSource;
        this.ussdRemoteDataSource = ussdRemoteDataSource;
    }

    @Override
    public Flowable<UssdResponse> confirmByPhone(int receiptId) {
        return ussdRemoteDataSource.confirmByPhone(receiptId);
    }
}
