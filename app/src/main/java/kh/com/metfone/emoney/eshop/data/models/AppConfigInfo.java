package kh.com.metfone.emoney.eshop.data.models;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by Administrator on 3/13/2018.
 */

public class AppConfigInfo extends RealmObject {
    @SerializedName("appConfigId")
    private int appConfigId;
    @SerializedName("status")
    private int status;
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

    public int getAppConfigId() {
        return appConfigId;
    }

    public int getStatus() {
        return status;
    }

    public String getConfigKey() {
        return configKey;
    }

    public String getConfigCode() {
        return configCode;
    }

    public String getConfigValue() {
        return configValue;
    }

    public String getConfigName() {
        return configName;
    }

    public String getDescription() {
        return description;
    }

    public void setAppConfigId(int appConfigId) {
        this.appConfigId = appConfigId;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    public void setConfigCode(String configCode) {
        this.configCode = configCode;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
