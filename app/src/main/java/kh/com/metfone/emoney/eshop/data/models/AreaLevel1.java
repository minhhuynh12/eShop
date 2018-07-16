package kh.com.metfone.emoney.eshop.data.models;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by DCV on 3/23/2018.
 */

public class AreaLevel1 extends RealmObject{
    @SerializedName("areaCode")
    private String areaCode;
    @SerializedName("areaType")
    private String areaType;

    @SerializedName("parentCode")
    private String parentCode;
    @SerializedName("province")
    private String province;
    @SerializedName("district")
    private String district;
    @SerializedName("precinct")
    private String precinct;
    @SerializedName("streetBlock")
    private String streetBlock;
    @SerializedName("street")
    private String street;
    @SerializedName("areaName")
    private String areaName;
    @SerializedName("fullName")
    private String fullName;
    @SerializedName("orderNo")
    private String orderNo;
    @SerializedName("status")
    private String status;
    @SerializedName("areas")
    private RealmList<AreaLevel2> areaLevel2List;


    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaType() {
        return areaType;
    }

    public void setAreaType(String areaType) {
        this.areaType = areaType;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPrecinct() {
        return precinct;
    }

    public void setPrecinct(String precinct) {
        this.precinct = precinct;
    }

    public String getStreetBlock() {
        return streetBlock;
    }

    public void setStreetBlock(String streetBlock) {
        this.streetBlock = streetBlock;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public RealmList<AreaLevel2> getAreaLevel2List() {
        return areaLevel2List;
    }

    public void setAreaLevel2List(RealmList<AreaLevel2> areaLevel2List) {
        this.areaLevel2List = areaLevel2List;
    }
}
