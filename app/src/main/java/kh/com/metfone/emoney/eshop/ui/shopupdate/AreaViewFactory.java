package kh.com.metfone.emoney.eshop.ui.shopupdate;

import android.view.View;

import me.texy.treeview.base.BaseNodeViewBinder;
import me.texy.treeview.base.BaseNodeViewFactory;


/**
 * Created by zxy on 17/4/23.
 */

public class AreaViewFactory extends BaseNodeViewFactory {

    @Override
    public BaseNodeViewBinder getNodeViewBinder(View view, int level) {
        switch (level) {
            case 0:
                return new AreaGroupViewBinder(view);
            case 1:
                return new AreaFirstViewBinder(view);
            case 2:
                return new AreaSecondViewBinder(view);
            default:
                return null;
        }
    }
}
