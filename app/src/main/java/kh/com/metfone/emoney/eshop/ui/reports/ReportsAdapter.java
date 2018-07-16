package kh.com.metfone.emoney.eshop.ui.reports;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import kh.com.metfone.emoney.eshop.R;
import kh.com.metfone.emoney.eshop.data.models.ReceiptByStaff;
import kh.com.metfone.emoney.eshop.utils.DataUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by DCV on 3/7/2018.
 */

public class ReportsAdapter extends RecyclerView.Adapter<ReportsAdapter.ReportsViewHolder> {
    private List<ReceiptByStaff> receiptByStaffList;

    public ReportsAdapter() {
        this.receiptByStaffList = new ArrayList<>();
    }

    public void setList(List<ReceiptByStaff> mList) {
        this.receiptByStaffList.clear();
        this.receiptByStaffList.addAll(mList);
        notifyDataSetChanged();
    }

    public void clearList() {
        this.receiptByStaffList.clear();
        notifyDataSetChanged();
    }

    @Override
    public ReportsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_reports, parent, false);
        return new ReportsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReportsViewHolder holder, int position) {
        ReceiptByStaff receiptByStaff = receiptByStaffList.get(position);
        holder.txtStaffName.setText(receiptByStaff.getStaffName());
        holder.txtReceiptTotal.setText(String.valueOf(receiptByStaff.getTotalReceipt()));
        holder.txtKhrMoneyTotal.setText(DataUtils.formatterLong.format(Double.parseDouble(receiptByStaff.getTotalAmountKhr())));
        holder.txtUsdMoneyTotal.setText(DataUtils.getDoubleString(Double.parseDouble(receiptByStaff.getTotalAmountUsd())));
    }

    @Override
    public int getItemCount() {
        return receiptByStaffList.size() > 0 ? receiptByStaffList.size() : 0;
    }

    public class ReportsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_staff_name)
        TextView txtStaffName;
        @BindView(R.id.txt_receipts_total)
        TextView txtReceiptTotal;
        @BindView(R.id.txt_khr_money_total)
        TextView txtKhrMoneyTotal;
        @BindView(R.id.txt_usd_money_total)
        TextView txtUsdMoneyTotal;
        View itemview;

        public ReportsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.itemview = itemView;
        }
    }
}
