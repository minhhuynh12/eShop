package kh.com.metfone.emoney.eshop.data.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by DCV on 3/2/2018.
 */

public class Category implements Parcelable{
    private String name;
    private boolean isFavorite;

    public Category(String name, boolean isChoose) {
        this.name = name;
        this.isFavorite = isChoose;
    }

    protected Category(Parcel in) {
        name = in.readString();
        isFavorite = in.readByte() != 0;
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeByte((byte) (isFavorite ? 1 : 0));
    }
}
