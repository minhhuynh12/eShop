package kh.com.metfone.emoney.eshop.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by DCV on 3/7/2018.
 */

public class Receipt extends BaseResult implements Parcelable {
    private String invoiceTitle;
    private String staffName;
    private String time;
    private String moneyTotal;
    private String monetaryUnits;
    @SerializedName("receiptInfo")
    private ReceiptInfo receiptInfo;
    private String customerPhone;
    private String paymentCurrencyCode;

    public Receipt() {
    }

    protected Receipt(Parcel in) {
        invoiceTitle = in.readString();
        staffName = in.readString();
        time = in.readString();
        moneyTotal = in.readString();
        monetaryUnits = in.readString();
        customerPhone = in.readString();
       // paymentCurrencyCode = in.readString();
    }

    public static final Creator<Receipt> CREATOR = new Creator<Receipt>() {
        @Override
        public Receipt createFromParcel(Parcel in) {
            return new Receipt(in);
        }

        @Override
        public Receipt[] newArray(int size) {
            return new Receipt[size];
        }
    };

    public String getPaymentCurrencyCode() {
        return paymentCurrencyCode;
    }

    public void setPaymentCurrencyCode(String paymentCurrencyCode) {
        this.paymentCurrencyCode = paymentCurrencyCode;
    }

    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMoneyTotal() {
        return moneyTotal;
    }

    public void setMoneyTotal(String moneyTotal) {
        this.moneyTotal = moneyTotal;
    }

    public String getMonetaryUnits() {
        return monetaryUnits;
    }

    public void setMonetaryUnits(String monetaryUnits) {
        this.monetaryUnits = monetaryUnits;
    }

    public ReceiptInfo getReceiptInfo() {
        return receiptInfo;
    }

    public void setReceiptInfo(ReceiptInfo receiptInfo) {
        this.receiptInfo = receiptInfo;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(invoiceTitle);
        dest.writeString(staffName);
        dest.writeString(time);
        dest.writeString(moneyTotal);
        dest.writeString(monetaryUnits);
        dest.writeString(customerPhone);
        dest.writeString(paymentCurrencyCode);
    }
}
