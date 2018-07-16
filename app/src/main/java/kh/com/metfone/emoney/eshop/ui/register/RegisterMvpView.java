package kh.com.metfone.emoney.eshop.ui.register;

import kh.com.metfone.emoney.eshop.ui.base.BaseMvpView;

/**
 * Created by DCV on 3/1/2018.
 */

public interface RegisterMvpView extends BaseMvpView {
    void notifiEmptyFields();

    void onSuccessRegister(String message);
}
