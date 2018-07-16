package kh.com.metfone.emoney.eshop.data.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by DCV on 3/9/2018.
 */

public class VersionInformation extends BaseResult{
    @SerializedName("versionInfo")
    private VersionInforChild versionInforChild;

    public VersionInforChild getVersionInforChild() {
        return versionInforChild;
    }

    public void setVersionInforChild(VersionInforChild versionInforChild) {
        this.versionInforChild = versionInforChild;
    }
}
