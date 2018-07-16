package kh.com.metfone.emoney.eshop.data.event;

/**
 * Created by DCV on 3/27/2018.
 */

public class ChangeExchangeRateSuccess {
    private String exchangeRate;
    private String message;
    public ChangeExchangeRateSuccess(String exchangeRate, String message) {
        this.exchangeRate = exchangeRate;
        this.message = message;
    }

    public String getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(String exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
