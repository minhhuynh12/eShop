package kh.com.metfone.emoney.eshop.data.event;

import kh.com.metfone.emoney.eshop.data.models.ShopInfor;

/**
 * Created by DCV on 3/23/2018.
 */

public class ViewDetailShopEvent {
    private ShopInfor shopInfor;

    public ViewDetailShopEvent(ShopInfor info) {
        this.shopInfor = info;
    }

    public ShopInfor getShopInfor() {
        return shopInfor;
    }
}
