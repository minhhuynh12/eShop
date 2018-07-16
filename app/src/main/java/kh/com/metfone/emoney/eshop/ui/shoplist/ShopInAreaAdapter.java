package kh.com.metfone.emoney.eshop.ui.shoplist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import kh.com.metfone.emoney.eshop.R;
import kh.com.metfone.emoney.eshop.data.models.ShopInfor;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by Administrator on 3/7/2018.
 */

public class ShopInAreaAdapter extends RecyclerView.Adapter<ShopInAreaAdapter.ViewHolder> {
    private List<ShopInfor> shopList;
    private PublishSubject<ShopInfor> shopInforPublishSubject;

    public ShopInAreaAdapter(List<ShopInfor> list, PublishSubject<ShopInfor> shopInforPublishSubject) {
        this.shopList = list;
        this.shopInforPublishSubject = shopInforPublishSubject;
    }

    @Override
    public ShopInAreaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_shop_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ShopInAreaAdapter.ViewHolder holder, int position) {
        holder.setData(shopList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shopInforPublishSubject.onNext(shopList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return shopList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_shop_name)
        TextView txtProvinceName;
        @BindView(R.id.txt_full_address)
        TextView txtFullAddress;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        void setData(ShopInfor shopInfor) {
            txtProvinceName.setText(shopInfor.getShopName());
            txtFullAddress.setText(shopInfor.getShopAddress());
        }
    }
}
