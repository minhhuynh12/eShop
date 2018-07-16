package kh.com.metfone.emoney.eshop.data.local;

import kh.com.metfone.emoney.eshop.data.ShopDataSource;
import kh.com.metfone.emoney.eshop.data.models.ChainList;
import kh.com.metfone.emoney.eshop.data.models.UpdateShopInfo;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/15/2018.
 */

public class ShopLocalDataSource implements ShopDataSource {

    @Override
    public Flowable<ChainList> getChainList() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Flowable<UpdateShopInfo> updateShop(String shopName, String contactPhone, String contactEmail, String areaCode, String shopAddress) {
        throw new UnsupportedOperationException();
    }
}
