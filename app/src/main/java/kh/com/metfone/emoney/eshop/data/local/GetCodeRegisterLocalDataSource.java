package kh.com.metfone.emoney.eshop.data.local;

import kh.com.metfone.emoney.eshop.data.GetCodeRegisterDataSource;
import kh.com.metfone.emoney.eshop.data.models.BaseResult;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/12/2018.
 */

public class GetCodeRegisterLocalDataSource implements GetCodeRegisterDataSource {

    @Override
    public Flowable<BaseResult> getRegisterCode(String phoneNumber) {
        return null;
    }
}
