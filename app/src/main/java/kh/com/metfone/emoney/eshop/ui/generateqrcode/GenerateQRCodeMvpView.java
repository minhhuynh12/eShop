package kh.com.metfone.emoney.eshop.ui.generateqrcode;

import kh.com.metfone.emoney.eshop.data.models.CommonConfigInfo;
import kh.com.metfone.emoney.eshop.data.models.Receipt;
import kh.com.metfone.emoney.eshop.data.models.ShopConfig;
import kh.com.metfone.emoney.eshop.data.models.UserInformation;
import kh.com.metfone.emoney.eshop.ui.base.BaseMvpView;

/**
 * Created by DCV on 3/6/2018.
 */

public interface GenerateQRCodeMvpView extends BaseMvpView {

    void newReceiptSuccess(Receipt receipt);

    void newReceiptFailed(String messageError);

    void getConfigSuccess(CommonConfigInfo commonConfigInfo);

    void getConfigFailed(String messageError);

    void getConfigSuccess(ShopConfig shopConfig);

    void getUserInfoSuccess(UserInformation userInformation);
}
