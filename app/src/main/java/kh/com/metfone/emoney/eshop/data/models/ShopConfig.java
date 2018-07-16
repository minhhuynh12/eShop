package kh.com.metfone.emoney.eshop.data.models;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by DCV on 3/27/2018.
 */

public class ShopConfig extends RealmObject{
    @SerializedName("shopConfigId")
    private String shopConfigId;
    @SerializedName("shopId")
    private String shopId;
    @SerializedName("status")
    private String status;
    @SerializedName("configKey")
    private String configKey;
    @SerializedName("configCode")
    private String configCode;
    @SerializedName("configValue")
    private String configValue;
    @SerializedName("configName")
    private String configName;
    @SerializedName("description")
    private String description;
    @SerializedName("createTime")
    private String createTime;
    @SerializedName("createUser")
    private String createUser;
    @SerializedName("updateTime")
    private String updateTime;
    @SerializedName("updateUser")
    private String updateUser;

    public String getShopConfigId() {
        return shopConfigId;
    }

    public void setShopConfigId(String shopConfigId) {
        this.shopConfigId = shopConfigId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getConfigKey() {
        return configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    public String getConfigCode() {
        return configCode;
    }

    public void setConfigCode(String configCode) {
        this.configCode = configCode;
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }
}
