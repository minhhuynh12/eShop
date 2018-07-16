package kh.com.metfone.emoney.eshop.data.event;

import kh.com.metfone.emoney.eshop.data.models.ShopSubTypeInfo;

/**
 * Created by DCV on 3/21/2018.
 */

public class ChooseCategoryEvent {
    private ShopSubTypeInfo shopInfor;
    public ChooseCategoryEvent(ShopSubTypeInfo shopInfor) {
        this.shopInfor = shopInfor;
    }

    public ShopSubTypeInfo getShopInfor() {
        return shopInfor;
    }
}
