package kh.com.metfone.emoney.eshop.data.models;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Administrator on 3/13/2018.
 */

public class CommonConfigInfo extends RealmObject {

    @SerializedName("appConfigs")
    private RealmList<AppConfigInfo> appConfigInfo = null;
    @SerializedName("shopTypes")
    private RealmList<ShopTypeInfo> shopeTypes= null;
    @SerializedName("status")
    private int status;
    @SerializedName("code")
    private String code;
    @SerializedName("message")
    private String message;


    public int getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public RealmList<ShopTypeInfo> getShopeTypes() {
        return shopeTypes;
    }

    public RealmList<AppConfigInfo> getAppConfigInfo() {
        return appConfigInfo;
    }

}
