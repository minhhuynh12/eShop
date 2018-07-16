package kh.com.metfone.emoney.eshop.data.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 3/20/2018.
 */

public class ReceipsInformation {
    @SerializedName("transReceiptId")
    private String transReceiptId;
    @SerializedName("receiptCode")
    private String receiptCode;
    @SerializedName("shopId")
    private String shopId;
    @SerializedName("staffId")
    private String staffId;
    @SerializedName("status")
    private String status;
    @SerializedName("receiptType")
    private String receiptType;
    @SerializedName("invoiceTitle")
    private String invoiceTitle;
    @SerializedName("currencyCode")
    private String currencyCode;
    @SerializedName("amount")
    private String amount;
    @SerializedName("discount")
    private String discount;
    @SerializedName("discountType")
    private String discountType;
    @SerializedName("discountAmount")
    private String discountAmount;
    @SerializedName("totalAmount")
    private String totalAmount;
    @SerializedName("createTime")
    private String createTime;
    @SerializedName("paidTime")
    private String paidTime;
    @SerializedName("paidTid")
    private String paidTid;
    @SerializedName("paidEmoneyMsisdn")
    private String paidEmoneyMsisdn;
    @SerializedName("paidFee")
    private String paidFee;
    @SerializedName("paidChanel")
    private String paidChanel;
    @SerializedName("description")
    private String description;
    @SerializedName("staffCode")
    private String staffCode;
    @SerializedName("staffName")
    private String staffName;
    @SerializedName("collectDate")
    private String collectDate;

    public ReceipsInformation (String title, String staff, String time, String amount, String unit){

    }

    public String getTransReceiptId() {
        return transReceiptId;
    }

    public String getReceiptCode() {
        return receiptCode;
    }

    public String getShopId() {
        return shopId;
    }

    public String getStaffId() {
        return staffId;
    }

    public String getStatus() {
        return status;
    }

    public String getReceiptType() {
        return receiptType;
    }

    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public String getAmount() {
        return amount;
    }

    public String getDiscount() {
        return discount;
    }

    public String getDiscountType() {
        return discountType;
    }

    public String getDiscountAmount() {
        return discountAmount;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getPaidTime() {
        return paidTime;
    }

    public String getPaidTid() {
        return paidTid;
    }

    public String getPaidEmoneyMsisdn() {
        return paidEmoneyMsisdn;
    }

    public String getPaidFee() {
        return paidFee;
    }

    public String getPaidChanel() {
        return paidChanel;
    }

    public String getDescription() {
        return description;
    }

    public String getStaffCode() {
        return staffCode;
    }

    public String getStaffName() {
        return staffName;
    }

    public String getCollectDate() {
        return collectDate;
    }

    public void setTransReceiptId(String transReceiptId) {
        this.transReceiptId = transReceiptId;
    }

    public void setReceiptCode(String receiptCode) {
        this.receiptCode = receiptCode;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setReceiptType(String receiptType) {
        this.receiptType = receiptType;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public void setDiscountAmount(String discountAmount) {
        this.discountAmount = discountAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setPaidTime(String paidTime) {
        this.paidTime = paidTime;
    }

    public void setPaidTid(String paidTid) {
        this.paidTid = paidTid;
    }

    public void setPaidEmoneyMsisdn(String paidEmoneyMsisdn) {
        this.paidEmoneyMsisdn = paidEmoneyMsisdn;
    }

    public void setPaidFee(String paidFee) {
        this.paidFee = paidFee;
    }

    public void setPaidChanel(String paidChanel) {
        this.paidChanel = paidChanel;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public void setCollectDate(String collectDate) {
        this.collectDate = collectDate;
    }
}
