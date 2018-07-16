package kh.com.metfone.emoney.eshop.ui.chooseshop;

import android.view.View;
import android.widget.TextView;

import kh.com.metfone.emoney.eshop.R;
import kh.com.metfone.emoney.eshop.data.models.AreaShop;

import me.texy.treeview.TreeNode;
import me.texy.treeview.base.BaseNodeViewBinder;

/**
 * Created by DCV on 06/03/2018.
 */

public class ShopGroupViewBinder extends BaseNodeViewBinder {
    TextView textView;
//    ImageView imageView;
    public ShopGroupViewBinder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.txt_category_group);
//        imageView = (ImageView) itemView.findViewById(R.id.img_check);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_shop_group;
    }

    @Override
    public void bindView(final TreeNode treeNode) {
        AreaShop areaShop = (AreaShop) treeNode.getValue();
        textView.setText(areaShop.getProvinceName());
    }

    @Override
    public void onNodeToggled(TreeNode treeNode, boolean expand) {
    }
}
