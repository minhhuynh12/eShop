package kh.com.metfone.emoney.eshop.ui.staffdetail;

import kh.com.metfone.emoney.eshop.data.models.StaffInShop;
import kh.com.metfone.emoney.eshop.ui.base.BaseMvpView;

/**
 * Created by Administrator on 3/7/2018.
 */

public interface StaffDetailMvpView extends BaseMvpView {
    void resetPasswordForStaffSuccess(String message);

    void resetPasswordForStaffFailed(String errorMessage);

    void getStaffInfoSuccess(StaffInShop staffInShop);
}
