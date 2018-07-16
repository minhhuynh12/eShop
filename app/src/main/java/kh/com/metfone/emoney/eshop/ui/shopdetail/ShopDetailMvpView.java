package kh.com.metfone.emoney.eshop.ui.shopdetail;

import kh.com.metfone.emoney.eshop.data.models.UserInformation;
import kh.com.metfone.emoney.eshop.ui.base.BaseMvpView;

/**
 * Created by Administrator on 3/7/2018.
 */

public interface ShopDetailMvpView extends BaseMvpView {
    void getUserInfoSuccess(UserInformation userInformation);

    void getUserInfoFailed(String message);
}
