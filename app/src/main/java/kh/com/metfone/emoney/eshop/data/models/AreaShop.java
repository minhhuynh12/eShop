package kh.com.metfone.emoney.eshop.data.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 3/7/2018.
 */

public class AreaShop {
    private String name;
    private List<ShopInArea> listShop;
    @SerializedName("provinceCode")
    private String provinceCode;
    @SerializedName("provinceName")
    private String provinceName;
    @SerializedName("shops")
    private List<ShopInfor> shopInforList;

    public String getName() {
        return name;
    }

    public List<ShopInArea> getListShop() {
        return listShop;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setListShop(List<ShopInArea> listShop) {
        this.listShop = listShop;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public List<ShopInfor> getShopInforList() {
        return shopInforList;
    }

    public void setShopInforList(List<ShopInfor> shopInforList) {
        this.shopInforList = shopInforList;
    }
}
