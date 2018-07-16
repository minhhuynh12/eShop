package kh.com.metfone.emoney.eshop.data.models;

/**
 * Created by Administrator on 3/7/2018.
 */

public class ShopInArea {
    private String avatar;
    private String name;
    private String code;

    public String getAvatar() {
        return avatar;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ShopInArea(String avatar, String name, String code) {
        this.avatar = avatar;
        this.name = name;
        this.code = code;
    }
}
