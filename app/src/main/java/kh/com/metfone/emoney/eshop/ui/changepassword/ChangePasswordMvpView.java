package kh.com.metfone.emoney.eshop.ui.changepassword;

import kh.com.metfone.emoney.eshop.ui.base.BaseMvpView;

/**
 * Created by DCV on 3/5/2018.
 */

public interface ChangePasswordMvpView extends BaseMvpView {
    void changePasswordSuccess(String message);

    void changePasswordFailed(String messageError);
}
