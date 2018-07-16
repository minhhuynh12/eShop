package kh.com.metfone.emoney.eshop.data.models;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by Administrator on 3/13/2018.
 */

public class ShopSubTypeInfo extends RealmObject {
    @SerializedName("shopTypeId")
    private int shopTypeId;
    @SerializedName("parentShopTypeId")
    private int parentShopTypeId;
    @SerializedName("shopTypeCode")
    private String shopTypeCode;
    @SerializedName("shopTypeName")
    private String shopTypeName;
    @SerializedName("status")
    private int status;
    @SerializedName("description")
    private String description;
    @SerializedName("subShopTypes")
    private String subShopTypes;

    public int getShopTypeId() {
        return shopTypeId;
    }

    public int getParentShopTypeId() {
        return parentShopTypeId;
    }

    public String getShopTypeCode() {
        return shopTypeCode;
    }

    public String getShopTypeName() {
        return shopTypeName;
    }

    public int getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public String getSubShopTypes() {
        return subShopTypes;
    }

    public void setShopTypeId(int shopTypeId) {
        this.shopTypeId = shopTypeId;
    }

    public void setParentShopTypeId(int parentShopTypeId) {
        this.parentShopTypeId = parentShopTypeId;
    }

    public void setShopTypeCode(String shopTypeCode) {
        this.shopTypeCode = shopTypeCode;
    }

    public void setShopTypeName(String shopTypeName) {
        this.shopTypeName = shopTypeName;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSubShopTypes(String subShopTypes) {
        this.subShopTypes = subShopTypes;
    }
}
