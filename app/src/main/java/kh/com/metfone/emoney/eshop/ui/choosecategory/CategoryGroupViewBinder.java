package kh.com.metfone.emoney.eshop.ui.choosecategory;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import kh.com.metfone.emoney.eshop.R;
import kh.com.metfone.emoney.eshop.data.event.ChooseCategoryParentEvent;
import kh.com.metfone.emoney.eshop.data.models.ShopTypeInfo;

import org.greenrobot.eventbus.EventBus;

import me.texy.treeview.TreeNode;
import me.texy.treeview.base.BaseNodeViewBinder;

/**
 * Created by DCV on 06/03/2018.
 */

public class CategoryGroupViewBinder extends BaseNodeViewBinder {
    TextView textView;
    ImageView imageView;
    public CategoryGroupViewBinder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.txt_category_group);
        imageView = (ImageView) itemView.findViewById(R.id.img_check);
    }


    @Override
    public int getLayoutId() {
        return R.layout.item_category_group;
    }

    @Override
    public void bindView(final TreeNode treeNode) {
        ShopTypeInfo shopTypeInfo = (ShopTypeInfo) treeNode.getValue();
        textView.setText(shopTypeInfo.getShopTypeName());
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new ChooseCategoryParentEvent(shopTypeInfo));
            }
        });
}

    @Override
    public void onNodeToggled(TreeNode treeNode, boolean expand) {
    }
}
