package kh.com.metfone.emoney.eshop.ui.setting;

import kh.com.metfone.emoney.eshop.data.models.AppConfigInfo;
import kh.com.metfone.emoney.eshop.data.models.ShopConfig;
import kh.com.metfone.emoney.eshop.data.models.UserInformation;
import kh.com.metfone.emoney.eshop.ui.base.BaseMvpView;

/**
 * Created by DCV on 3/5/2018.
 */

public interface SettingMvpView extends BaseMvpView {
    void logoutSuccess();

    void logoutFailed(String messageError);

    void getPrinterNameSuccess(String printerName);


    void getConfigSuccess(ShopConfig shopConfig);

    void getConfigFailed(String messageError);

    void getConfigSuccess(AppConfigInfo appConfigInfo);

    void getUserInfoSuccess(UserInformation userInformation);
}
