package kh.com.metfone.emoney.eshop.ui.confirmphone;

import kh.com.metfone.emoney.eshop.data.models.Receipt;
import kh.com.metfone.emoney.eshop.data.models.UserInformation;
import kh.com.metfone.emoney.eshop.ui.base.BaseMvpView;

/**
 * Created by DCV on 3/6/2018.
 */

public interface CheckStatusUSSDMvpView extends BaseMvpView{
    void alreadyUSSDSuccess(Receipt receipt);
    void showPaidStatus(String message);

    void getUserInfoSuccess(UserInformation userInformation);
}
