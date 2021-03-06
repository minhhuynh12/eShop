package kh.com.metfone.emoney.eshop.ui.chooseshop;

import android.view.View;

import me.texy.treeview.base.BaseNodeViewBinder;
import me.texy.treeview.base.BaseNodeViewFactory;


/**
 * Created by zxy on 17/4/23.
 */

public class ShopViewFactory extends BaseNodeViewFactory {

    @Override
    public BaseNodeViewBinder getNodeViewBinder(View view, int level) {
        switch (level) {
            case 0:
                return new ShopGroupViewBinder(view);
            case 1:
                return new ShopChildViewBinder(view);
            default:
                return null;
        }
    }
}
