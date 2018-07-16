package kh.com.metfone.emoney.eshop.ui.generateqrcode;

import kh.com.metfone.emoney.eshop.data.models.Receipt;
import kh.com.metfone.emoney.eshop.data.models.UserInformation;
import kh.com.metfone.emoney.eshop.ui.base.BaseMvpView;

/**
 * Created by DCV on 3/6/2018.
 */

public interface CheckStatusQRCodeMvpView extends BaseMvpView{

    void alreadyPaidSuccess(Receipt receipt);

    void showPaidStatus(String message);

    void getUserInfoSuccess(UserInformation userInformation);

    void getPrinterAddressSuccess(String printerAddress);
}
