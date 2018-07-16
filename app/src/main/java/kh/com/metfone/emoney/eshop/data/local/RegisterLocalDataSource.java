package kh.com.metfone.emoney.eshop.data.local;

import kh.com.metfone.emoney.eshop.data.RegisterDataSource;
import kh.com.metfone.emoney.eshop.data.models.BaseResult;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/12/2018.
 */

public class RegisterLocalDataSource implements RegisterDataSource {


    @Override
    public Flowable<BaseResult> register(String username, String privateCode, String password, String category_id, String shopname, String address) {
        return null;
    }
}
