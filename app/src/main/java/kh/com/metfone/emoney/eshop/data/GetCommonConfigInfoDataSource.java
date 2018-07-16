package kh.com.metfone.emoney.eshop.data;


import kh.com.metfone.emoney.eshop.data.models.BaseResult;
import kh.com.metfone.emoney.eshop.data.models.CommonConfigInfo;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/9/2018.
 */


public interface GetCommonConfigInfoDataSource {
    Flowable<CommonConfigInfo> getCommonConfigInformation();
    Flowable<CommonConfigInfo> getCommonConfigInfo();
    Flowable<BaseResult> changeExchangeRate(String khrMoney);

    void saveCommonConfigInfo(CommonConfigInfo commonConfigInfo);

    void clearCommonConfigInfo();
}

