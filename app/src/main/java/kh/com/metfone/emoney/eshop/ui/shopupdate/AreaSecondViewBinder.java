package kh.com.metfone.emoney.eshop.ui.shopupdate;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import kh.com.metfone.emoney.eshop.ChooseAreaEvent;
import kh.com.metfone.emoney.eshop.R;
import kh.com.metfone.emoney.eshop.data.models.AreaLevel3;

import org.greenrobot.eventbus.EventBus;

import me.texy.treeview.TreeNode;
import me.texy.treeview.base.BaseNodeViewBinder;

/**
 * Created by DCV on 6/3/2018.
 */

public class AreaSecondViewBinder extends BaseNodeViewBinder {

    TextView txtChildContent;
    ImageView imgCheck;

    public AreaSecondViewBinder(View itemView) {
        super(itemView);
        txtChildContent = (TextView) itemView.findViewById(R.id.txt_child_content);
        imgCheck = (ImageView) itemView.findViewById(R.id.img_check);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_area_level2_child;
    }

    @Override
    public void bindView(final TreeNode treeNode) {
        AreaLevel3 areaLevel3 = (AreaLevel3) treeNode.getValue();
        txtChildContent.setText(areaLevel3.getAreaName());
        imgCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new ChooseAreaEvent(areaLevel3.getAreaCode(), areaLevel3.getFullName()));
            }
        });
    }

    @Override
    public void onNodeToggled(TreeNode treeNode, boolean expand) {
    }
}
