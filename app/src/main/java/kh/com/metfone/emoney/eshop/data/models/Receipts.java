package kh.com.metfone.emoney.eshop.data.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by DCV on 3/7/2018.
 */

public class Receipts {
    @SerializedName("collectDate")
    private String date;
    @SerializedName("totalAmountKhr")
    private String khrMoneyTotal;
    @SerializedName("totalAmountUsd")
    private String usdMoneyTotal;
    @SerializedName("receipts")
    private List<ReceipsInformation> receiptList;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getKhrMoneyTotal() {
        return khrMoneyTotal;
    }

    public void setKhrMoneyTotal(String khrMoneyTotal) {
        this.khrMoneyTotal = khrMoneyTotal;
    }

    public String getUsdMoneyTotal() {
        return usdMoneyTotal;
    }

    public void setUsdMoneyTotal(String usdMoneyTotal) {
        this.usdMoneyTotal = usdMoneyTotal;
    }

    public List<ReceipsInformation> getReceiptList() {
        return receiptList;
    }

    public void setReceiptList(List<ReceipsInformation> receiptList) {
        this.receiptList = receiptList;
    }
}
