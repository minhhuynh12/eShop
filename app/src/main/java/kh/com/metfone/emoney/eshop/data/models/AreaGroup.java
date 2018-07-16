package kh.com.metfone.emoney.eshop.data.models;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by DCV on 3/23/2018.
 */

public class AreaGroup extends RealmObject {
    private int id = 1010;
    @SerializedName("status")
    private int status;
    @SerializedName("code")
    private String code;
    @SerializedName("message")
    private String message;

    @SerializedName("areas")
    private RealmList<AreaLevel1> areaLevel1List;

    public RealmList<AreaLevel1> getAreaLevel1List() {
        return areaLevel1List;
    }

    public void setAreaLevel1List(RealmList<AreaLevel1> areaLevel1List) {
        this.areaLevel1List = areaLevel1List;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
