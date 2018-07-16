package kh.com.metfone.emoney.eshop.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Administrator on 3/7/2018.
 */

public class StaffInShop extends RealmObject implements Parcelable {
    public static String KEY_STAFF_ID = "staffId";
    @SerializedName("shopName")
    private String shopName;
    @SerializedName("staffCode")
    private String staffCode;
    @PrimaryKey
    @SerializedName("staffId")
    private String staffId;
    @SerializedName("status")
    private String status;
    @SerializedName("shopId")
    private String shopId;
    @SerializedName("staffType")
    private String staffType;
    @SerializedName("password")
    private String password;
    @SerializedName("staffName")
    private String staffName;
    @SerializedName("msisdn")
    private String msisdn;
    @SerializedName("startTime")
    private String startTime;
    @SerializedName("createTime")
    private String createTime;
    @SerializedName("createUser")
    private String createUser;
    @SerializedName("firebaseToken")
    private String firebaseToken;
    @SerializedName("deviceToken")
    private String deviceToken;
    @SerializedName("lastLoginTime")
    private String lastLoginTime;
    @SerializedName("lastLoginOs")
    private String lastLoginOs;
    @SerializedName("numLoginFail")
    private String numLoginFail;

    public StaffInShop() {
    }

    protected StaffInShop(Parcel in) {
        shopName = in.readString();
        staffCode = in.readString();
        staffId = in.readString();
        status = in.readString();
        shopId = in.readString();
        staffType = in.readString();
        password = in.readString();
        staffName = in.readString();
        msisdn = in.readString();
        startTime = in.readString();
        createTime = in.readString();
        createUser = in.readString();
        firebaseToken = in.readString();
        deviceToken = in.readString();
        lastLoginTime = in.readString();
        lastLoginOs = in.readString();
        numLoginFail = in.readString();
    }

    public static final Creator<StaffInShop> CREATOR = new Creator<StaffInShop>() {
        @Override
        public StaffInShop createFromParcel(Parcel in) {
            return new StaffInShop(in);
        }

        @Override
        public StaffInShop[] newArray(int size) {
            return new StaffInShop[size];
        }
    };

    public String getShopName() {
        return shopName;
    }

    public String getStaffCode() {
        return staffCode;
    }


    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode;
    }

    public StaffInShop(String name, String code) {
        this.shopName = name;
        this.staffCode = code;
    }

    public String getStaffId() {
        return staffId;
    }

    public String getStatus() {
        return status;
    }

    public String getShopId() {
        return shopId;
    }

    public String getStaffType() {
        return staffType;
    }

    public String getPassword() {
        return password;
    }

    public String getStaffName() {
        return staffName;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public String getFirebaseToken() {
        return firebaseToken;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public String getLastLoginOs() {
        return lastLoginOs;
    }

    public String getNumLoginFail() {
        return numLoginFail;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public void setStaffType(String staffType) {
        this.staffType = staffType;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public void setFirebaseToken(String firebaseToken) {
        this.firebaseToken = firebaseToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public void setLastLoginOs(String lastLoginOs) {
        this.lastLoginOs = lastLoginOs;
    }

    public void setNumLoginFail(String numLoginFail) {
        this.numLoginFail = numLoginFail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(shopName);
        dest.writeString(staffCode);
        dest.writeString(staffId);
        dest.writeString(status);
        dest.writeString(shopId);
        dest.writeString(staffType);
        dest.writeString(password);
        dest.writeString(staffName);
        dest.writeString(msisdn);
        dest.writeString(startTime);
        dest.writeString(createTime);
        dest.writeString(createUser);
        dest.writeString(firebaseToken);
        dest.writeString(deviceToken);
        dest.writeString(lastLoginTime);
        dest.writeString(lastLoginOs);
        dest.writeString(numLoginFail);
    }
}
