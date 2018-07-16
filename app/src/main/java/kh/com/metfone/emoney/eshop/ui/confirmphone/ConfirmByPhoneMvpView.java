package kh.com.metfone.emoney.eshop.ui.confirmphone;

import kh.com.metfone.emoney.eshop.data.models.CommonConfigInfo;
import kh.com.metfone.emoney.eshop.data.models.Receipt;
import kh.com.metfone.emoney.eshop.data.models.ShopConfig;
import kh.com.metfone.emoney.eshop.data.models.UserInformation;
import kh.com.metfone.emoney.eshop.ui.base.BaseMvpView;

/**
 * Created by DCV on 3/6/2018.
 */

public interface ConfirmByPhoneMvpView extends BaseMvpView {
    void newReceiptPhoneSuccess(Receipt receipt);

    void newReceiptPhoneFailed(String messageError);

    void getConfigSuccess(CommonConfigInfo commonConfigInfo);

    void getConfigFailed(String messageError);

    void getUserInfoSuccess(UserInformation userInformation);

    void getConfigSuccess(ShopConfig shopConfig);
}
