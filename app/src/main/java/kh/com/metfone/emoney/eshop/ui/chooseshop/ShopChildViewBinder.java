package kh.com.metfone.emoney.eshop.ui.chooseshop;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import kh.com.metfone.emoney.eshop.R;
import kh.com.metfone.emoney.eshop.data.event.ChooseShopEvent;
import kh.com.metfone.emoney.eshop.data.models.ShopInfor;

import org.greenrobot.eventbus.EventBus;

import me.texy.treeview.TreeNode;
import me.texy.treeview.base.BaseNodeViewBinder;

/**
 * Created by DCV on 6/3/2018.
 */

public class ShopChildViewBinder extends BaseNodeViewBinder {

    TextView txtCategoryChild;
    ImageView imgChoose;

    public ShopChildViewBinder(View itemView) {
        super(itemView);
        txtCategoryChild = (TextView) itemView.findViewById(R.id.txt_category_child);
        imgChoose = (ImageView) itemView.findViewById(R.id.img_check);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_shop_child;
    }

    @Override
    public void bindView(final TreeNode treeNode) {
        ShopInfor shopInfor = (ShopInfor) treeNode.getValue();
        txtCategoryChild.setText(shopInfor.getShopName());
        imgChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new ChooseShopEvent(shopInfor));
            }
        });
    }

    @Override
    public void onNodeToggled(TreeNode treeNode, boolean expand) {
        /*treeNode.getValue();
        treeView.getSelectedNodes().add(treeNode);*/
    }
}
