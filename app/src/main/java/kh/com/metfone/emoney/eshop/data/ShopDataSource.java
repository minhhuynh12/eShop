package kh.com.metfone.emoney.eshop.data;

import kh.com.metfone.emoney.eshop.data.models.ChainList;
import kh.com.metfone.emoney.eshop.data.models.UpdateShopInfo;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/20/2018.
 */

public interface ShopDataSource {
    Flowable<ChainList> getChainList();

    Flowable<UpdateShopInfo> updateShop(String shopName, String contactPhone, String contactEmail, String areaCode, String shopAddress);
}
