package kh.com.metfone.emoney.eshop.data;

import kh.com.metfone.emoney.eshop.data.models.BaseResult;
import kh.com.metfone.emoney.eshop.injection.Local;
import kh.com.metfone.emoney.eshop.injection.Remote;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/12/2018.
 */

public class GetCodeRegisterRepository implements GetCodeRegisterDataSource {
    private final GetCodeRegisterDataSource localRegisterDataSource;
    private final GetCodeRegisterDataSource remoteRegisterDataSource;

    @Inject
    GetCodeRegisterRepository(@Local GetCodeRegisterDataSource localRegisterDataSource,
                              @Remote GetCodeRegisterDataSource remoteRegisterDataSource) {
        this.localRegisterDataSource = localRegisterDataSource;
        this.remoteRegisterDataSource = remoteRegisterDataSource;
    }

    @Override
    public Flowable<BaseResult> getRegisterCode(String phoneNumber) {
        return remoteRegisterDataSource.getRegisterCode(phoneNumber);
    }
}
