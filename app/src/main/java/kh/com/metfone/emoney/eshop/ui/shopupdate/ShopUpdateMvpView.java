package kh.com.metfone.emoney.eshop.ui.shopupdate;

import kh.com.metfone.emoney.eshop.data.models.UpdateShopInfo;
import kh.com.metfone.emoney.eshop.ui.base.BaseMvpView;

/**
 * Created by Administrator on 3/7/2018.
 */

public interface ShopUpdateMvpView extends BaseMvpView {
    void updateShopInfoSuccess(UpdateShopInfo updateShopInfo);
    void updateShopInfoFailed(String message);
}
