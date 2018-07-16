package kh.com.metfone.emoney.eshop.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by DCV on 3/13/2018.
 */

public class ReceiptInfo implements Parcelable {
    @SerializedName("transReceiptId")
    private int transReceiptId;
    @SerializedName("receiptCode")
    private String receiptCode;
    @SerializedName("shopId")
    private int shopId;
    @SerializedName("staffId")
    private int staffId;
    @SerializedName("status")
    private int status;
    @SerializedName("receiptType")
    private int receiptType;
    @SerializedName("invoiceTitle")
    private String invoiceTitle;
    @SerializedName("currencyCode")
    private String currencyCode;
    @SerializedName("amount")
    private String amount;
    @SerializedName("discount")
    private String discount;
    @SerializedName("discountType")
    private int discountType;
    @SerializedName("discountAmount")
    private String discountAmount;
    @SerializedName("totalAmount")
    private String totalAmount;
    @SerializedName("createTime")
    private long createTime;
    @SerializedName("paidTime")
    private long paidTime;
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

    @SerializedName("paidAmount")
    private String paidAmount;

    @SerializedName("totalAmountConvert")
    private String totalAmountConvert;

    @SerializedName("paymentCurrencyCode")
    private String paymentCurrencyCode;

    @SerializedName("paymentTip")
    private String paymentTip;

    @SerializedName("paymentTotalAmount")
    private double paymentTotalAmount;

    @SerializedName("staffCode")
    private String staffCode;
    @SerializedName("staffName")
    private String staffName;
    @SerializedName("collectDate")
    private String collectDate;

    protected ReceiptInfo(Parcel in) {
        transReceiptId = in.readInt();
        receiptCode = in.readString();
        shopId = in.readInt();
        staffId = in.readInt();
        status = in.readInt();
        receiptType = in.readInt();
        invoiceTitle = in.readString();
        currencyCode = in.readString();
        amount = in.readString();
        discount = in.readString();
        discountType = in.readInt();
        discountAmount = in.readString();
        totalAmount = in.readString();
        createTime = in.readLong();
        paidTime = in.readLong();
        paidTid = in.readString();
        paidEmoneyMsisdn = in.readString();
        paidFee = in.readString();
        paidChanel = in.readString();
        description = in.readString();
        paidAmount = in.readString();
        totalAmountConvert = in.readString();
        paymentCurrencyCode = in.readString();
        paymentTip = in.readString();
        staffCode = in.readString();
        staffName = in.readString();
        collectDate = in.readString();
        paymentTotalAmount = in.readDouble();
    }

    public ReceiptInfo() {
    }

    public static final Creator<ReceiptInfo> CREATOR = new Creator<ReceiptInfo>() {
        @Override
        public ReceiptInfo createFromParcel(Parcel in) {
            return new ReceiptInfo(in);
        }

        @Override
        public ReceiptInfo[] newArray(int size) {
            return new ReceiptInfo[size];
        }
    };

    public double getPaymentTotalAmount() {
        return paymentTotalAmount;
    }

    public void setPaymentTotalAmount(double paymentTotalAmount) {
        this.paymentTotalAmount = paymentTotalAmount;
    }

    public String getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(String paidAmount) {
        this.paidAmount = paidAmount;
    }

    public String getPaymentTip() {
        return paymentTip;
    }

    public void setPaymentTip(String paymentTip) {
        this.paymentTip = paymentTip;
    }

    public String getPaymentCurrencyCode() {
        return paymentCurrencyCode;
    }

    public void setPaymentCurrencyCode(String paymentCurrencyCode) {
        this.paymentCurrencyCode = paymentCurrencyCode;
    }

    public String getTotalAmountConvert() {
        return totalAmountConvert;
    }

    public void setTotalAmountConvert(String totalAmountConvert) {
        this.totalAmountConvert = totalAmountConvert;
    }

    public int getTransReceiptId() {
        return transReceiptId;
    }

    public void setTransReceiptId(int transReceiptId) {
        this.transReceiptId = transReceiptId;
    }

    public String getReceiptCode() {
        return receiptCode;
    }

    public void setReceiptCode(String receiptCode) {
        this.receiptCode = receiptCode;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getReceiptType() {
        return receiptType;
    }

    public void setReceiptType(int receiptType) {
        this.receiptType = receiptType;
    }

    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public int getDiscountType() {
        return discountType;
    }

    public void setDiscountType(int discountType) {
        this.discountType = discountType;
    }

    public String getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(String discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getPaidTime() {
        return paidTime;
    }

    public void setPaidTime(long paidTime) {
        this.paidTime = paidTime;
    }

    public String getPaidTid() {
        return paidTid;
    }

    public void setPaidTid(String paidTid) {
        this.paidTid = paidTid;
    }

    public String getPaidEmoneyMsisdn() {
        return paidEmoneyMsisdn;
    }

    public void setPaidEmoneyMsisdn(String paidEmoneyMsisdn) {
        this.paidEmoneyMsisdn = paidEmoneyMsisdn;
    }

    public String getPaidFee() {
        return paidFee;
    }

    public void setPaidFee(String paidFee) {
        this.paidFee = paidFee;
    }

    public String getPaidChanel() {
        return paidChanel;
    }

    public void setPaidChanel(String paidChanel) {
        this.paidChanel = paidChanel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getCollectDate() {
        return collectDate;
    }

    public void setCollectDate(String collectDate) {
        this.collectDate = collectDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(transReceiptId);
        dest.writeString(receiptCode);
        dest.writeInt(shopId);
        dest.writeInt(staffId);
        dest.writeInt(status);
        dest.writeInt(receiptType);
        dest.writeString(invoiceTitle);
        dest.writeString(currencyCode);
        dest.writeString(amount);
        dest.writeString(discount);
        dest.writeInt(discountType);
        dest.writeString(discountAmount);
        dest.writeString(totalAmount);
        dest.writeLong(createTime);
        dest.writeLong(paidTime);
        dest.writeString(paidTid);
        dest.writeString(paidEmoneyMsisdn);
        dest.writeString(paidFee);
        dest.writeString(paidChanel);
        dest.writeString(description);
        dest.writeString(staffCode);
        dest.writeString(staffName);
        dest.writeString(collectDate);
    }
}
