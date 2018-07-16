package kh.com.metfone.emoney.eshop.ui.shoplist;

import android.content.Context;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import kh.com.metfone.emoney.eshop.R;
import kh.com.metfone.emoney.eshop.data.models.AreaShop;
import kh.com.metfone.emoney.eshop.data.models.ShopInfor;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by Administrator on 3/7/2018.
 */

public class AreaShopAdapter extends RecyclerView.Adapter<AreaShopAdapter.ViewHolder> {
    private List<AreaShop> shopList;
    private Context mContext;
    private PublishSubject<ShopInfor> shopInforPublishSubject;

    public AreaShopAdapter() {
        this.shopList = new ArrayList<>();
        this.shopInforPublishSubject = PublishSubject.create();
    }

    public void setList(List<AreaShop> mList) {
        this.shopList.clear();
        this.shopList.addAll(mList);
        notifyDataSetChanged();
    }

    public PublishSubject<ShopInfor> getShopInforPublishSubject() {
        return shopInforPublishSubject;
    }

    @Override
    public AreaShopAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.mContext = parent.getContext();
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_area_shop_row, parent, false));
    }

    @Override
    public void onBindViewHolder(AreaShopAdapter.ViewHolder holder, int position) {
        holder.setData(shopList.get(position));

    }

    @Override
    public int getItemCount() {
        return shopList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_province_name)
        TextView txtProvinceName;
        @BindView(R.id.rc_shop_in_area)
        public RecyclerView rcShopInArea;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        void setData(AreaShop item) {
            txtProvinceName.setText(item.getProvinceName());
            ShopInAreaAdapter adapter = new ShopInAreaAdapter(item.getShopInforList(), shopInforPublishSubject);
            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
            rcShopInArea.setLayoutManager(layoutManager);
            rcShopInArea.setNestedScrollingEnabled(false);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mContext,
                    layoutManager.getOrientation());
            rcShopInArea.addItemDecoration(dividerItemDecoration);
            rcShopInArea.setAdapter(adapter);
        }
    }
}
