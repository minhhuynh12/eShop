package kh.com.metfone.emoney.eshop.ui.shopupdate;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import kh.com.metfone.emoney.eshop.ChooseAreaEvent;
import kh.com.metfone.emoney.eshop.R;
import kh.com.metfone.emoney.eshop.data.models.AreaLevel2;

import org.greenrobot.eventbus.EventBus;

import me.texy.treeview.TreeNode;
import me.texy.treeview.base.BaseNodeViewBinder;

/**
 * Created by DCV on 6/3/2018.
 */

public class AreaFirstViewBinder extends BaseNodeViewBinder {

    TextView txtAreaChild;
    ImageView imgCheck;
    public AreaFirstViewBinder(View itemView) {
        super(itemView);
        txtAreaChild = (TextView) itemView.findViewById(R.id.txt_child_content);
        imgCheck = (ImageView) itemView.findViewById(R.id.img_check);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_area_level1_child;
    }

    @Override
    public void bindView(final TreeNode treeNode) {
        AreaLevel2 areaLevel2 = (AreaLevel2) treeNode.getValue();
        txtAreaChild.setText(areaLevel2.getAreaName());
        imgCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new ChooseAreaEvent(areaLevel2.getAreaCode(), areaLevel2.getFullName()));
            }
        });
    }

    @Override
    public void onNodeToggled(TreeNode treeNode, boolean expand) {
    }
}
