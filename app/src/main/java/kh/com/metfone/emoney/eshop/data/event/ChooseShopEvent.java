package kh.com.metfone.emoney.eshop.data.event;

import kh.com.metfone.emoney.eshop.data.models.ShopInfor;

/**
 * Created by DCV on 3/21/2018.
 */

public class ChooseShopEvent {
    private ShopInfor shopInfor;
    public ChooseShopEvent(ShopInfor shopInfor) {
        this.shopInfor = shopInfor;
    }

    public ShopInfor getShopInfor() {
        return shopInfor;
    }
}
