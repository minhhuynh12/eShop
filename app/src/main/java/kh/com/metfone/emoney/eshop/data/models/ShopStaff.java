package kh.com.metfone.emoney.eshop.data.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 3/7/2018.
 */

public class ShopStaff {
    @SerializedName("shopId")
    private String shopId;
    @SerializedName("shopName")
    private String shopName;
    @SerializedName("staffs")
    private List<StaffInShop> staffs;

    public String getShopName() {
        return shopName;
    }

    public List<StaffInShop> getListStaff() {
        return staffs;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public void setListStaff(List<StaffInShop> listStaff) {
        this.staffs = listStaff;
    }

    public String getShopId() {
        return shopId;
    }

    public List<StaffInShop> getStaffs() {
        return staffs;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public void setStaffs(List<StaffInShop> staffs) {
        this.staffs = staffs;
    }
}
