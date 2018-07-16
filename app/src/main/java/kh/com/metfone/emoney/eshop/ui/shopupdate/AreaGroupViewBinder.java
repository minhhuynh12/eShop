package kh.com.metfone.emoney.eshop.ui.shopupdate;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import kh.com.metfone.emoney.eshop.ChooseAreaEvent;
import kh.com.metfone.emoney.eshop.R;
import kh.com.metfone.emoney.eshop.data.models.AreaLevel1;

import org.greenrobot.eventbus.EventBus;

import me.texy.treeview.TreeNode;
import me.texy.treeview.base.BaseNodeViewBinder;

/**
 * Created by DCV on 06/03/2018.
 */

public class AreaGroupViewBinder extends BaseNodeViewBinder {
    TextView textView;
    ImageView imgCheck;
    public AreaGroupViewBinder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.txt_category_group);
        imgCheck = (ImageView) itemView.findViewById(R.id.img_check);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_area_group;
    }

    @Override
    public void bindView(final TreeNode treeNode) {
        AreaLevel1 areaLevel1 = (AreaLevel1) treeNode.getValue();
        textView.setText(areaLevel1.getAreaName());
        imgCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new ChooseAreaEvent(areaLevel1.getAreaCode(), areaLevel1.getFullName()));
            }
        });
    }

    @Override
    public void onNodeToggled(TreeNode treeNode, boolean expand) {
    }
}
