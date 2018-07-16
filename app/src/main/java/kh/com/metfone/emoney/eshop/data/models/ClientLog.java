package kh.com.metfone.emoney.eshop.data.models;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by DCV on 3/16/2018.
 */

public class ClientLog extends RealmObject {
    public static final String IS_lOG_SERVER_SUCCESS = "isLogServerSuccess";
    @PrimaryKey
    private long id;
    @SerializedName("loggedToken")
    private String loggedToken;
    @SerializedName("staffCode")
    private String staffCode;
    @SerializedName("apiName")
    private String apiName;
    @SerializedName("platform")
    private String platform = "android";
    @SerializedName("clientClass")
    private String clientClass;
    @SerializedName("clientMethod")
    private String clientMethod;
    @SerializedName("clientVersion")
    private String clientVersion = "1.8";
    @SerializedName("errorCode")
    private int errorCode;
    @SerializedName("requestContent")
    private String requestContent;
    @SerializedName("responseContent")
    private String responseContent;
    @SerializedName("startTime")
    private String startTime;
    @SerializedName("endTime")
    private String endTime;
    @SerializedName("duration")
    private long duration;
    @SerializedName("status")
    private int status;

    public ClientLog(String startTime,
                     Long id,
                     String loggedToken,
                     String staffCode,
                     String apiName,
                     String clientClass,
                     String clientMethod) {
        this.startTime = startTime;
        this.id = id;
        this.loggedToken = loggedToken;
        this.apiName = apiName;
        this.staffCode = staffCode;
        this.clientClass = clientClass;
        this.clientMethod = clientMethod;
    }

    private boolean isLogServerSuccess;

    public ClientLog() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLoggedToken() {
        return loggedToken;
    }

    public void setLoggedToken(String loggedToken) {
        this.loggedToken = loggedToken;
    }

    public String getStaffCode() {
        return staffCode;
    }

    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getClientClass() {
        return clientClass;
    }

    public void setClientClass(String clientClass) {
        this.clientClass = clientClass;
    }

    public String getClientMethod() {
        return clientMethod;
    }

    public void setClientMethod(String clientMethod) {
        this.clientMethod = clientMethod;
    }

    public String getClientVersion() {
        return clientVersion;
    }

    public void setClientVersion(String clientVersion) {
        this.clientVersion = clientVersion;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getRequestContent() {
        return requestContent;
    }

    public void setRequestContent(String requestContent) {
        this.requestContent = requestContent;
    }

    public String getResponseContent() {
        return responseContent;
    }

    public void setResponseContent(String responseContent) {
        this.responseContent = responseContent;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isLogServerSuccess() {
        return isLogServerSuccess;
    }

    public void setLogServerSuccess(boolean logServerSuccess) {
        isLogServerSuccess = logServerSuccess;
    }
}
