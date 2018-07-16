package kh.com.metfone.emoney.eshop.ui.shopupdate;

import kh.com.metfone.emoney.eshop.data.models.AreaGroup;
import kh.com.metfone.emoney.eshop.ui.base.BaseMvpView;

/**
 * Created by DCV on 3/2/2018.
 */

public interface ChooseAreaMvpView extends BaseMvpView {
    void getAreaGroupSuccess(AreaGroup areaGroup);

    void getAreaGroupFailed(String message);
}
