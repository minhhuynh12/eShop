package kh.com.metfone.emoney.eshop.ui.choosecategory;

import kh.com.metfone.emoney.eshop.data.models.ShopTypeInfo;
import kh.com.metfone.emoney.eshop.ui.base.BaseMvpView;

import java.util.List;

/**
 * Created by DCV on 3/2/2018.
 */

public interface ChooseCategoryMvpView extends BaseMvpView {
    void getCommonInfoSuccess(List<ShopTypeInfo> listShopType);

    void getCommonInfoFailed(String message);
}
