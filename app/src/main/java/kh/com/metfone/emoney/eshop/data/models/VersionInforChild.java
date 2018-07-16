package kh.com.metfone.emoney.eshop.data.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by DCV on 3/9/2018.
 */

public class VersionInforChild {
    @SerializedName("confidId")
    private int configId;
    @SerializedName("configKey")
    private String configKey;
    @SerializedName("newVersion")
    private String newVersion;
    @SerializedName("forceUpgrade")
    private boolean forceUpgrade;
    @SerializedName("installationUrl")
    private String installationUrl;
    @SerializedName("upgradeDescription")
    private String upgradeDescription;

    public int getConfigId() {
        return configId;
    }

    public void setConfigId(int configId) {
        this.configId = configId;
    }

    public String getConfigKey() {
        return configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    public String getNewVersion() {
        return newVersion;
    }

    public void setNewVersion(String newVersion) {
        this.newVersion = newVersion;
    }

    public boolean isForceUpgrade() {
        return forceUpgrade;
    }

    public void setForceUpgrade(boolean forceUpgrade) {
        this.forceUpgrade = forceUpgrade;
    }

    public String getInstallationUrl() {
        return installationUrl;
    }

    public void setInstallationUrl(String installationUrl) {
        this.installationUrl = installationUrl;
    }

    public String getUpgradeDescription() {
        return upgradeDescription;
    }

    public void setUpgradeDescription(String upgradeDescription) {
        this.upgradeDescription = upgradeDescription;
    }
}
