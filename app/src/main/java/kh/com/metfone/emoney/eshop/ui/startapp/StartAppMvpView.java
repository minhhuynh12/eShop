package kh.com.metfone.emoney.eshop.ui.startapp;

import kh.com.metfone.emoney.eshop.data.models.UserInformation;
import kh.com.metfone.emoney.eshop.data.models.VersionInformation;
import kh.com.metfone.emoney.eshop.ui.base.BaseMvpView;

/**
 * Created by DCV on 3/9/2018.
 */

public interface StartAppMvpView extends BaseMvpView {
    void checkVersionAppSuccess(VersionInformation information);

    void checkVersionAppFailed(String errorMessage);

    void goNextScreen(boolean isLogin);

    void getUserInforSuccess(UserInformation userInformation);

    void setLocale(String language);
}
