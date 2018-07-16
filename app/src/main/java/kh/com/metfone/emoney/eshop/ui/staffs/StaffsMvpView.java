package kh.com.metfone.emoney.eshop.ui.staffs;

import kh.com.metfone.emoney.eshop.data.models.StaffsResponse;
import kh.com.metfone.emoney.eshop.ui.base.BaseMvpView;

/**
 * Created by DCV on 3/2/2018.
 */

public interface StaffsMvpView extends BaseMvpView{

    void getStaffsListSuccess(StaffsResponse receipt);

    void getStaffsListFailed(String errorMessage);
}
