package kh.com.metfone.emoney.eshop.data.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 3/21/2018.
 */

public class StaffsResponse {
    @SerializedName("status")
    private String status;
    @SerializedName("code")
    private String code;
    @SerializedName("message")
    private String message;
    @SerializedName("listStaff")
    private List<ShopStaff> listStaff;

    public String getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ShopStaff> getListStaff() {
        return listStaff;
    }

    public void setListStaff(List<ShopStaff> listStaff) {
        this.listStaff = listStaff;
    }
}
