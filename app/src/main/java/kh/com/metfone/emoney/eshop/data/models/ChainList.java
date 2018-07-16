package kh.com.metfone.emoney.eshop.data.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by DCV on 3/20/2018.
 */

public class ChainList extends BaseResult {
    @SerializedName("chainInfo")
    private List<AreaShop> areaShopList;

    public List<AreaShop> getAreaShopList() {
        return areaShopList;
    }

    public void setAreaShopList(List<AreaShop> areaShopList) {
        this.areaShopList = areaShopList;
    }
}
