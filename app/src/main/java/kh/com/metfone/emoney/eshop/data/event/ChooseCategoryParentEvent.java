package kh.com.metfone.emoney.eshop.data.event;

import kh.com.metfone.emoney.eshop.data.models.ShopTypeInfo;

/**
 * Created by DCV on 3/21/2018.
 */

public class ChooseCategoryParentEvent {
    private ShopTypeInfo shopInfor;
    public ChooseCategoryParentEvent(ShopTypeInfo shopInfor) {
        this.shopInfor = shopInfor;
    }

    public ShopTypeInfo getShopInfor() {
        return shopInfor;
    }
}
