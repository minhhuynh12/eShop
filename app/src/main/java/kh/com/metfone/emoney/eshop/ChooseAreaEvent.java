package kh.com.metfone.emoney.eshop;

/**
 * Created by DCV on 3/23/2018.
 */

public class ChooseAreaEvent {
    private String areaCode;
    private String areaName;

    public ChooseAreaEvent(String areaCode, String areaName) {
        this.areaCode = areaCode;
        this.areaName = areaName;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public String getAreaName() {
        return areaName;
    }
}
