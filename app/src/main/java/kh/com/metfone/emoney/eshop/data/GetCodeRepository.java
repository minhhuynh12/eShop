package kh.com.metfone.emoney.eshop.data;

import kh.com.metfone.emoney.eshop.data.models.BaseResult;
import kh.com.metfone.emoney.eshop.injection.Local;
import kh.com.metfone.emoney.eshop.injection.Remote;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/12/2018.
 */

public class GetCodeRepository implements GetCodeDataSource {
    private final GetCodeDataSource localGetCodeDataSource;
    private final GetCodeDataSource remoteGetCodeDataSource;

    @Inject
    GetCodeRepository(@Local GetCodeDataSource localGetCodeDataSource,
                      @Remote GetCodeDataSource remoteGetCodeDataSource) {
        this.localGetCodeDataSource = localGetCodeDataSource;
        this.remoteGetCodeDataSource = remoteGetCodeDataSource;
    }

    @Override
    public Flowable<BaseResult> getCode(String phoneNumber) {
        return remoteGetCodeDataSource.getCode(phoneNumber);
    }
}
