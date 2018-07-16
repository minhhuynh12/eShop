package kh.com.metfone.emoney.eshop.ui.newreceipt;

import kh.com.metfone.emoney.eshop.data.models.UserInformation;
import kh.com.metfone.emoney.eshop.ui.base.BaseMvpView;

/**
 * Created by DCV on 3/15/2018.
 */

public interface ViewReceiptMvpView extends BaseMvpView {

    void getPrinterAddressSuccess(String printerAddress);

    void getUserInfoSuccess(UserInformation userInformation);

    void getUserLogin(String username);
}
