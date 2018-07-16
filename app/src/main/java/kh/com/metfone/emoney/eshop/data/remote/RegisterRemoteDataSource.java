package kh.com.metfone.emoney.eshop.data.remote;

import kh.com.metfone.emoney.eshop.consts.Const;
import kh.com.metfone.emoney.eshop.data.RegisterDataSource;
import kh.com.metfone.emoney.eshop.data.models.BaseResult;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/12/2018.
 */
@Singleton
public class RegisterRemoteDataSource implements RegisterDataSource {
    private final RetrofitService retrofitService;

    @Inject
    public RegisterRemoteDataSource(RetrofitService retrofitService) {
        this.retrofitService = retrofitService;
    }

    @Override
    public Flowable<BaseResult> register(String username, String privateCode, String password, String category_id, String shopname, String address) {
        Map<String, String> body = new HashMap<>();
        body.put(Const.KEY_MSISDN, username);
        body.put(Const.KEY_PRIVATE_KEY, privateCode);
        body.put(Const.KEY_PASSWORD, password);
        body.put(Const.KEY_SHOP_TYPE_ID, category_id);
        body.put(Const.KEY_SHOP_NAME, shopname);
        body.put(Const.KEY_SHOP_ADDRESS, address);
        return retrofitService.register(body);
    }
}
