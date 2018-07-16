package kh.com.metfone.emoney.eshop.ui.setting;

import kh.com.metfone.emoney.eshop.data.event.ChangeExchangeRateSuccess;
import kh.com.metfone.emoney.eshop.ui.base.BaseMvpView;

/**
 * Created by DCV on 3/16/2018.
 */

public interface ChangeExchangeRateMvpView extends BaseMvpView {
    void changeExchangeRateSuccess(ChangeExchangeRateSuccess changeExchangeRateSuccess);

    void changeExchangeRateFailed(String messageError);
}
