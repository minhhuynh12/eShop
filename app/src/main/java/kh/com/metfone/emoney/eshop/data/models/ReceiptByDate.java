package kh.com.metfone.emoney.eshop.data.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by DCV on 3/20/2018.
 */

public class ReceiptByDate {
    @SerializedName("shopId")
    private String shopId;
    @SerializedName("collectDate")
    private String collectDate;
    @SerializedName("totalAmountUsd")
    private String totalAmountUsd;
    @SerializedName("totalAmountKhr")
    private String totalAmountKhr;

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getCollectDate() {
        return collectDate;
    }

    public void setCollectDate(String collectDate) {
        this.collectDate = collectDate;
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
