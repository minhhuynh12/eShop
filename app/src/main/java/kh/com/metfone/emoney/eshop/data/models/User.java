package kh.com.metfone.emoney.eshop.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by DCV on 3/9/2018.
 */

public class User extends RealmObject implements Parcelable {
    @SerializedName("staffId")
    private int staffId;
    @SerializedName("statusUser")
    private int statusUser;
    @SerializedName("shopId")
    private int shopId;
    @SerializedName("staffType")
    private int staffType;
    @SerializedName("staffCode")
    private String staffCode;
    @SerializedName("staffName")
    private String staffName;
    @SerializedName("msisdn")
    private String msisdn;
    @SerializedName("startTime")
    private String startTime;
    @SerializedName("firebaseToken")
    private String firebaseToken;
    @SerializedName("deviceToken")
    private String deviceToken;
    @SerializedName("accessToken")
    private String accessToken;

    public User() {
    }

    public User(Parcel in) {
        staffId = in.readInt();
        statusUser = in.readInt();
        shopId = in.readInt();
        staffType = in.readInt();
        staffCode = in.readString();
        staffName = in.readString();
        msisdn = in.readString();
        startTime = in.readString();
        firebaseToken = in.readString();
        deviceToken = in.readString();
        accessToken = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public int getStatusUser() {
        return statusUser;
    }

    public void setStatusUser(int statusUser) {
        this.statusUser = statusUser;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public int getStaffType() {
        return staffType;
    }

    public void setStaffType(int staffType) {
        this.staffType = staffType;
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

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getFirebaseToken() {
        return firebaseToken;
    }

    public void setFirebaseToken(String firebaseToken) {
        this.firebaseToken = firebaseToken;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(staffId);
        dest.writeInt(statusUser);
        dest.writeInt(shopId);
        dest.writeInt(staffType);
        dest.writeString(staffCode);
        dest.writeString(staffName);
        dest.writeString(msisdn);
        dest.writeString(startTime);
        dest.writeString(firebaseToken);
        dest.writeString(deviceToken);
        dest.writeString(accessToken);
    }
}
