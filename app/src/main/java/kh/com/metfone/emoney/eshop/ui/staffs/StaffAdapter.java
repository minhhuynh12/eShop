package kh.com.metfone.emoney.eshop.ui.staffs;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import kh.com.metfone.emoney.eshop.R;
import kh.com.metfone.emoney.eshop.data.models.StaffInShop;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by Administrator on 3/7/2018.
 */

public class StaffAdapter extends RecyclerView.Adapter<StaffAdapter.ViewHolder> {
    private List<StaffInShop> staff_list;
    private Context mContext;
    private PublishSubject<StaffInShop> staffInShopPublishSubject;

    public StaffAdapter(List<StaffInShop> list, PublishSubject<StaffInShop> staffInShopPublishSubject) {
        this.staff_list = list;
        this.staffInShopPublishSubject = staffInShopPublishSubject;
    }

    @Override
    public StaffAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_staff_row1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StaffAdapter.ViewHolder holder, int position) {
        holder.setData(staff_list.get(position), position);
    }

    @Override
    public int getItemCount() {
        return staff_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.staff_avatar)
        ImageView staff_avatar;
        @BindView(R.id.staff_name)
        TextView staff_name;
        @BindView(R.id.staff_code)
        TextView staff_code;
        @BindView(R.id.lin_staff) LinearLayout lin_staff;
        @BindView(R.id.img_line) ImageView img_line;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        void setData(StaffInShop items, int position) {
            staff_name.setText(items.getStaffName());
            staff_code.setText(items.getStaffCode());
            if (position == (staff_list.size() - 1)) {
                img_line.setVisibility(View.GONE);
            }
            lin_staff.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    staffInShopPublishSubject.onNext(items);
                }
            });
        }
    }
}
