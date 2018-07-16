package kh.com.metfone.emoney.eshop.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by DCV on 3/9/2018.
 */

public class ShopInfor extends RealmObject implements Parcelable {
    @SerializedName("shopId")
    private int shopId;
    @SerializedName("parentShopId")
    private int parentShopId;
    @SerializedName("status")
    private int statusShop;
    @SerializedName("verified")
    private int verified;
    @SerializedName("shopTypeId")
    private int shopTypeId;
    @SerializedName("shopName")
    private String shopName;
    @SerializedName("shopAddress")
    private String shopAddress;
    @SerializedName("areaCode")
    private String areaCode;
    @SerializedName("emoneyAccountMsisdn")
    private String emoneyAccountMsisdn;
    @SerializedName("emoneyAccountType")
    private int emoneyAccountType;
    @SerializedName("contactPhone")
    private String contactPhone;
    @SerializedName("contactEmail")
    private String contactEmail;
    @SerializedName("isChain")
    private int isChain;
    @SerializedName("isPrimary")
    private int isPrimary;
    @SerializedName("x")
    private String x;
    @SerializedName("y")
    private String y;
    @SerializedName("createTime")
    private String createTime;
    @SerializedName("provinceCode")
    private String provinceCode;
    @SerializedName("provinceName")
    private String provinceName;
    @SerializedName("areaFullName")
    private String areaFullName;

    public ShopInfor() {
    }

    public ShopInfor(Parcel in) {
        shopId = in.readInt();
        parentShopId = in.readInt();
        statusShop = in.readInt();
        verified = in.readInt();
        shopTypeId = in.readInt();
        shopName = in.readString();
        shopAddress = in.readString();
        areaCode = in.readString();
        emoneyAccountMsisdn = in.readString();
        emoneyAccountType = in.readInt();
        contactPhone = in.readString();
        contactEmail = in.readString();
        isChain = in.readInt();
        isPrimary = in.readInt();
        x = in.readString();
        y = in.readString();
        createTime = in.readString();
        provinceCode = in.readString();
        provinceName = in.readString();
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public int getParentShopId() {
        return parentShopId;
    }

    public void setParentShopId(int parentShopId) {
        this.parentShopId = parentShopId;
    }

    public int getStatusShop() {
        return statusShop;
    }

    public void setStatusShop(int statusShop) {
        this.statusShop = statusShop;
    }

    public int getVerified() {
        return verified;
    }

    public void setVerified(int verified) {
        this.verified = verified;
    }

    public int getShopTypeId() {
        return shopTypeId;
    }

    public void setShopTypeId(int shopTypeId) {
        this.shopTypeId = shopTypeId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getEmoneyAccountMsisdn() {
        return emoneyAccountMsisdn;
    }

    public void setEmoneyAccountMsisdn(String emoneyAccountMsisdn) {
        this.emoneyAccountMsisdn = emoneyAccountMsisdn;
    }

    public int getEmoneyAccountType() {
        return emoneyAccountType;
    }

    public void setEmoneyAccountType(int emoneyAccountType) {
        this.emoneyAccountType = emoneyAccountType;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public int getIsChain() {
        return isChain;
    }

    public void setIsChain(int isChain) {
        this.isChain = isChain;
    }

    public int getIsPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(int isPrimary) {
        this.isPrimary = isPrimary;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getAreaFullName() {
        return areaFullName;
    }

    public void setAreaFullName(String areaFullName) {
        this.areaFullName = areaFullName;
    }

    public static final Creator<ShopInfor> CREATOR = new Creator<ShopInfor>() {
        @Override
        public ShopInfor createFromParcel(Parcel in) {
            return new ShopInfor(in);
        }

        @Override
        public ShopInfor[] newArray(int size) {
            return new ShopInfor[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(shopId);
        dest.writeInt(parentShopId);
        dest.writeInt(statusShop);
        dest.writeInt(verified);
        dest.writeInt(shopTypeId);
        dest.writeString(shopName);
        dest.writeString(shopAddress);
        dest.writeString(areaCode);
        dest.writeString(emoneyAccountMsisdn);
        dest.writeInt(emoneyAccountType);
        dest.writeString(contactPhone);
        dest.writeString(contactEmail);
        dest.writeInt(isChain);
        dest.writeInt(isPrimary);
        dest.writeString(x);
        dest.writeString(y);
        dest.writeString(createTime);
        dest.writeString(provinceCode);
        dest.writeString(provinceName);
    }
}
