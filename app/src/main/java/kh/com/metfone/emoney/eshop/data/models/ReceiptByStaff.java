package kh.com.metfone.emoney.eshop.data.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by DCV on 3/20/2018.
 */

public class ReceiptByStaff {
    @SerializedName("staffId")
    private String staffId;
    @SerializedName("staffCode")
    private String staffCode;
    @SerializedName("staffName")
    private String staffName;
    @SerializedName("totalReceipt")
    private int totalReceipt;
    @SerializedName("totalAmountUsd")
    private String totalAmountUsd;
    @SerializedName("totalAmountKhr")
    private String totalAmountKhr;

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getStaffCode() {
        return staffCode;
    }

    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public int getTotalReceipt() {
        return totalReceipt;
    }

    public void setTotalReceipt(int totalReceipt) {
        this.totalReceipt = totalReceipt;
    }

    public String getTotalAmountUsd() {
        return totalAmountUsd;
    }

    public void setTotalAmountUsd(String totalAmountUsd) {
        this.totalAmountUsd = totalAmountUsd;
    }

    public String getTotalAmountKhr() {
        return totalAmountKhr;
    }

    public void setTotalAmountKhr(String totalAmountKhr) {
        this.totalAmountKhr = totalAmountKhr;
    }
}
