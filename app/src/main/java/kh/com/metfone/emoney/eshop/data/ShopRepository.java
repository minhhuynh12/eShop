package kh.com.metfone.emoney.eshop.data;

import kh.com.metfone.emoney.eshop.data.models.ChainList;
import kh.com.metfone.emoney.eshop.data.models.UpdateShopInfo;
import kh.com.metfone.emoney.eshop.injection.Local;
import kh.com.metfone.emoney.eshop.injection.Remote;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/15/2018.
 */

public class ShopRepository implements ShopDataSource {

    private final ShopDataSource shopRemoteDataSource;
    private final ShopDataSource shopLocalDataSource;

    @Inject
    ShopRepository(@Local ShopDataSource shopLocalDataSource,
                   @Remote ShopDataSource shopRemoteDataSource) {
        this.shopLocalDataSource = shopLocalDataSource;
        this.shopRemoteDataSource = shopRemoteDataSource;
    }

    @Override
    public Flowable<ChainList> getChainList() {
        return shopRemoteDataSource.getChainList();
    }

    @Override
    public Flowable<UpdateShopInfo> updateShop(String shopName, String contactPhone, String contactEmail, String areaCode, String shopAddress) {
        return shopRemoteDataSource.updateShop(shopName, contactPhone, contactEmail, areaCode, shopAddress);
    }
}
