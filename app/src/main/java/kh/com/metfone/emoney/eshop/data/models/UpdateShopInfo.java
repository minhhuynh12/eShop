package kh.com.metfone.emoney.eshop.data.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by DCV on 3/23/2018.
 */

public class UpdateShopInfo extends  BaseResult {
    @SerializedName("shopInfo")
    private ShopInfor shopInfor;

    public ShopInfor getShopInfor() {
        return shopInfor;
    }

    public void setShopInfor(ShopInfor shopInfor) {
        this.shopInfor = shopInfor;
    }
}
