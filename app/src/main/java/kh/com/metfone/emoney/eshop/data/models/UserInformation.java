package kh.com.metfone.emoney.eshop.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by DCV on 3/9/2018.
 */
public class UserInformation extends RealmObject implements Parcelable {
    @PrimaryKey
    private int id;
    @SerializedName("userInfo")
    private User userInfo;
    @SerializedName("shopInfo")
    private ShopInfor shopInfor;
    @SerializedName("status")
    private int status;
    @SerializedName("code")
    private String code;
    @SerializedName("message")
    private String message;
    @SerializedName("chain")
    private RealmList<ShopInfor> chain;

    @SerializedName("shopConfigs")
    private RealmList<ShopConfig> shopConfigs;

    public UserInformation() {
    }

    public User getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(User userInfo) {
        this.userInfo = userInfo;
    }

    public ShopInfor getShopInfor() {
        return shopInfor;
    }

    public void setShopInfor(ShopInfor shopInfor) {
        this.shopInfor = shopInfor;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RealmList<ShopInfor> getChain() {
        return chain;
    }

    public void setChain(RealmList<ShopInfor> chain) {
        this.chain = chain;
    }

    public RealmList<ShopConfig> getShopConfigs() {
        return shopConfigs;
    }

    public void setShopConfigs(RealmList<ShopConfig> shopConfigs) {
        this.shopConfigs = shopConfigs;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeParcelable(this.userInfo, flags);
        dest.writeParcelable(this.shopInfor, flags);
        dest.writeInt(this.status);
        dest.writeString(this.code);
        dest.writeString(this.message);
//        dest.writeTypedList(this.chain);
    }

    protected UserInformation(Parcel in) {
        this.id = in.readInt();
        this.userInfo = in.readParcelable(User.class.getClassLoader());
        this.shopInfor = in.readParcelable(ShopInfor.class.getClassLoader());
        this.status = in.readInt();
        this.code = in.readString();
        this.message = in.readString();
//        this.chain = in.createTypedArrayList(ShopInfor.CREATOR);
    }

    public static final Creator<UserInformation> CREATOR = new Creator<UserInformation>() {
        @Override
        public UserInformation createFromParcel(Parcel source) {
            return new UserInformation(source);
        }

        @Override
        public UserInformation[] newArray(int size) {
            return new UserInformation[size];
        }
    };
}
