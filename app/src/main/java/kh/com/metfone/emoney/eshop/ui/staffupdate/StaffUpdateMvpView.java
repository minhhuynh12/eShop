package kh.com.metfone.emoney.eshop.ui.staffupdate;

import kh.com.metfone.emoney.eshop.ui.base.BaseMvpView;

/**
 * Created by Administrator on 3/7/2018.
 */

public interface StaffUpdateMvpView extends BaseMvpView {
    void updateStaffSuccess(String fullname, String number_phone);
    void updateStaffFailed(String errorMessage);
}
