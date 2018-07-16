package kh.com.metfone.emoney.eshop.data.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by DCV on 3/9/2018.
 */

public class Report extends BaseResult {
    @SerializedName("totalAmountUsd")
    private String totalAmountUsd;
    @SerializedName("totalAmountKhr")
    private String totalAmountKhr;
    @SerializedName("receiptsByDate")
    private List<ReceiptByDate> receiptsByDate;
    @SerializedName("receiptsByStaff")
    private List<ReceiptByStaff> receiptsByStaff;

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

    public List<ReceiptByDate> getReceiptsByDate() {
        return receiptsByDate;
    }

    public void setReceiptsByDate(List<ReceiptByDate> receiptsByDate) {
        this.receiptsByDate = receiptsByDate;
    }

    public List<ReceiptByStaff> getReceiptsByStaff() {
        return receiptsByStaff;
    }

    public void setReceiptsByStaff(List<ReceiptByStaff> receiptsByStaff) {
        this.receiptsByStaff = receiptsByStaff;
    }
}
