package kh.com.metfone.emoney.eshop.ui.chooseshop;

import kh.com.metfone.emoney.eshop.data.models.ChainList;
import kh.com.metfone.emoney.eshop.ui.base.BaseMvpView;

/**
 * Created by DCV on 3/2/2018.
 */

public interface ChooseShopMvpView extends BaseMvpView {
    void getChainListSuccess(ChainList chainList);

    void getChainListFailed(String message);
}
