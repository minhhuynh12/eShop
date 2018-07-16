package kh.com.metfone.emoney.eshop.data.models;

/**
 * Created by Administrator on 3/23/2018.
 */

public class UssdResponse {
    private String status;
    private String code;
    private String message;
    private ReceiptInfo receiptInfo;

    public String getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public ReceiptInfo getReceiptInfo() {
        return receiptInfo;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setReceiptInfo(ReceiptInfo receiptInfo) {
        this.receiptInfo = receiptInfo;
    }
}
