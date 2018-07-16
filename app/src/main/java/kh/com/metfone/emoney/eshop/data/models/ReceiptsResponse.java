package kh.com.metfone.emoney.eshop.data.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 3/20/2018.
 */

public class ReceiptsResponse {
    @SerializedName("status")
    private int status;
    @SerializedName("code")
    private String code;
    @SerializedName("message")
    private String message;
    @SerializedName("totalAmountUsd")
    private String totalAmountUsd;
    @SerializedName("totalAmountKhr")
    private String totalAmountKhr;
    @SerializedName("receipts")
    private List<Receipts> receipts;

    public int getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getTotalAmountUsd() {
        return totalAmountUsd;
    }

    public String getTotalAmountKhr() {
        return totalAmountKhr;
    }

    public List<Receipts> getReceipts() {
        return receipts;
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

    public void setTotalAmountUsd(String totalAmountUsd) {
        this.totalAmountUsd = totalAmountUsd;
    }

    public void setTotalAmountKhr(String totalAmountKhr) {
        this.totalAmountKhr = totalAmountKhr;
    }

    public void setReceipts(List<Receipts> receipts) {
        this.receipts = receipts;
    }
}
