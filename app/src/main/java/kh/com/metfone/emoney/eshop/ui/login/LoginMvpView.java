package kh.com.metfone.emoney.eshop.ui.login;

import kh.com.metfone.emoney.eshop.data.models.UserInformation;
import kh.com.metfone.emoney.eshop.ui.base.BaseMvpView;

/**
 * Created by DCV on 3/1/2018.
 */

public interface LoginMvpView extends BaseMvpView {
    void loginSuccess(UserInformation userInformation);
    void loginFailed(String errorMessage);
}
