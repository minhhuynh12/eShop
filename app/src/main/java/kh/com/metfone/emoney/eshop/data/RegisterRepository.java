package kh.com.metfone.emoney.eshop.data;

import kh.com.metfone.emoney.eshop.data.models.BaseResult;
import kh.com.metfone.emoney.eshop.injection.Local;
import kh.com.metfone.emoney.eshop.injection.Remote;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/12/2018.
 */

public class RegisterRepository implements RegisterDataSource {
    private final RegisterDataSource localRegisterDataSource;
    private final RegisterDataSource remoteRegisterDataSource;

    @Inject
    public RegisterRepository(@Local RegisterDataSource localRegisterDataSource,
                              @Remote RegisterDataSource remoteRegisterDataSource) {
        this.localRegisterDataSource = localRegisterDataSource;
        this.remoteRegisterDataSource = remoteRegisterDataSource;
    }

    @Override
    public Flowable<BaseResult> register(String username, String private_Code, String password, String category_id, String shop_name, String address) {
        return remoteRegisterDataSource.register(username, private_Code, password, category_id, shop_name, address);
//        return null;
    }
}
