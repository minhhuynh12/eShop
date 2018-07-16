package kh.com.metfone.emoney.eshop.ui.receipts;

import android.content.Context;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import kh.com.metfone.emoney.eshop.R;
import kh.com.metfone.emoney.eshop.data.models.Receipts;
import kh.com.metfone.emoney.eshop.utils.DataUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by DCV on 3/7/2018.
 */

public class ReceiptsAdapter extends RecyclerView.Adapter<ReceiptsAdapter.ReceiptsViewHolder> {
    private List<Receipts> receiptsList;
    private Context context;

    public ReceiptsAdapter() {
        this.receiptsList = new ArrayList<>();
    }

    public void setList(List<Receipts> mList) {
        this.receiptsList.clear();
        this.receiptsList.addAll(mList);
        notifyDataSetChanged();
    }

    public void clearList() {
        this.receiptsList.clear();
        notifyDataSetChanged();
    }

    @Override
    public ReceiptsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_receipts, parent, false);
        return new ReceiptsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReceiptsViewHolder holder, int position) {
        Receipts receipts = receiptsList.get(position);
        holder.txtDate.setText(receipts.getDate());
        holder.txtKhrMoneyTotal.setText(DataUtils.formatterLong.format(Double.parseDouble(receipts.getKhrMoneyTotal())));
        holder.txtUsdMoneyTotal.setText(DataUtils.getDoubleString(Double.parseDouble(receipts.getUsdMoneyTotal())));
        ReceiptsChildAdapter receiptsChildAdapter = new ReceiptsChildAdapter(context);
        receiptsChildAdapter.setList(receipts.getReceiptList());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        holder.recReceiptsChild.setNestedScrollingEnabled(false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(holder.recReceiptsChild.getContext(),
                linearLayoutManager.getOrientation());
        holder.recReceiptsChild.addItemDecoration(dividerItemDecoration);
        holder.recReceiptsChild.setLayoutManager(linearLayoutManager);
        holder.recReceiptsChild.setAdapter(receiptsChildAdapter);


    }

    @Override
    public int getItemCount() {
        return receiptsList.size() > 0 ? receiptsList.size() : 0;
    }

    public class ReceiptsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_date)
        TextView txtDate;
        @BindView(R.id.txt_khr_money_total)
        TextView txtKhrMoneyTotal;
        @BindView(R.id.txt_usd_money_total)
        TextView txtUsdMoneyTotal;
        @BindView(R.id.rec_receipts_child)
        RecyclerView recReceiptsChild;
        View itemview;

        public ReceiptsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.itemview = itemView;
        }
    }
}
