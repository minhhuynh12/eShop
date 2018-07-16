package kh.com.metfone.emoney.eshop.ui.choosecategory;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import kh.com.metfone.emoney.eshop.R;
import kh.com.metfone.emoney.eshop.data.event.ChooseCategoryEvent;
import kh.com.metfone.emoney.eshop.data.models.ShopSubTypeInfo;

import org.greenrobot.eventbus.EventBus;

import me.texy.treeview.TreeNode;
import me.texy.treeview.base.BaseNodeViewBinder;

/**
 * Created by DCV on 6/3/2018.
 */

public class CategoryChildViewBinder extends BaseNodeViewBinder {

    TextView txtCategoryChild;
//    ImageView imgCheck;
//    AppCompatCheckBox checkBox;
    ImageView imgChoose;
    public CategoryChildViewBinder(View itemView) {
        super(itemView);
        txtCategoryChild = (TextView) itemView.findViewById(R.id.txt_category_child);
//        imgCheck = (ImageView) itemView.findViewById(R.id.img_check);
        imgChoose = (ImageView) itemView.findViewById(R.id.img_check);
//        checkBox = itemView.findViewById(R.id.checkBox);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_category_child;
    }

    @Override
    public void bindView(final TreeNode treeNode) {
        ShopSubTypeInfo shopInfor = (ShopSubTypeInfo) treeNode.getValue();
        txtCategoryChild.setText(shopInfor.getShopTypeName());
        imgChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new ChooseCategoryEvent(shopInfor));
            }
        });
    }

    @Override
    public void onNodeToggled(TreeNode treeNode, boolean expand) {
        /*treeNode.getValue();
        treeView.getSelectedNodes().add(treeNode);*/
    }
}
