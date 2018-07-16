package kh.com.metfone.emoney.eshop.ui.staffs;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import kh.com.metfone.emoney.eshop.R;
import kh.com.metfone.emoney.eshop.data.models.ShopStaff;
import kh.com.metfone.emoney.eshop.data.models.StaffInShop;

import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by Administrator on 3/7/2018.
 */

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {
    private List<ShopStaff> shops_list;
    private Context mContext;
    private PublishSubject<StaffInShop> staffInShopPublishSubject;

    public ShopAdapter(List<ShopStaff> list) {
        this.shops_list = list;
        this.staffInShopPublishSubject = PublishSubject.create();
    }

    public PublishSubject<StaffInShop> getStaffInShopPublishSubject() {
        return staffInShopPublishSubject;
    }

    @Override
    public ShopAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.mContext = parent.getContext();
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_staff_in_shop_row, parent, false));
    }

    @Override
    public void onBindViewHolder(ShopAdapter.ViewHolder holder, int position) {
        holder.setData(shops_list.get(position));

    }

    @Override
    public int getItemCount() {
        return shops_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.shop_name)
        TextView shop_name;
        @BindView(R.id.list_shop_staff)
        public RecyclerView list_staff_in_shop;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        void setData(ShopStaff item) {
            shop_name.setText(item.getShopName());
            StaffAdapter adapter = new StaffAdapter(item.getListStaff(), staffInShopPublishSubject);
            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
            list_staff_in_shop.setLayoutManager(layoutManager);
            /*SnapHelper snapHelper = new GravitySnapHelper(Gravity.START);
            try {
                snapHelper.attachToRecyclerView(rcShopInArea);
            } catch (IllegalStateException e) {
            }*/
            list_staff_in_shop.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }
}
