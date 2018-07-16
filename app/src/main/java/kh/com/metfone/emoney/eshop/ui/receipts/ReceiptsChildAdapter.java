package kh.com.metfone.emoney.eshop.ui.receipts;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import kh.com.metfone.emoney.eshop.R;
import kh.com.metfone.emoney.eshop.consts.Const;
import kh.com.metfone.emoney.eshop.data.models.ReceipsInformation;
import kh.com.metfone.emoney.eshop.utils.DataUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by DCV on 3/7/2018.
 */

public class ReceiptsChildAdapter extends RecyclerView.Adapter<ReceiptsChildAdapter.ReceiptsChildViewHolder> {
    Context context;
    private List<ReceipsInformation> receiptList;

    public void setList(List<ReceipsInformation> mList) {
        this.receiptList.clear();
        this.receiptList.addAll(mList);
        notifyDataSetChanged();
    }

    public ReceiptsChildAdapter(Context context) {
        this.receiptList = new ArrayList<>();
        this.context = context;
    }

    @Override
    public ReceiptsChildViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_receipts_child, parent, false);
        return new ReceiptsChildViewHolder(view);
    }

    public static String convertMillis(long milliseconds) {
        Date d = new Date(milliseconds);
        int hour = d.getHours();
        String hour_display = String.valueOf(hour);
        if (hour< 10) {
            hour_display = "0" + hour_display;
        }
        int munite = d.getMinutes();
        String munite_display = String.valueOf(munite);
        if (munite< 10) {
            munite_display = "0" + munite_display;
        }

        return (hour_display + ":" + munite_display);
    }


    @Override
    public void onBindViewHolder(ReceiptsChildViewHolder holder, int position) {
        ReceipsInformation receipt = receiptList.get(position);
        displayChildReceipt(receipt, holder.txtInVoiceTitle, holder.txtMoneyTotal);
        holder.txtTime.setText(convertMillis(Long.valueOf(receipt.getCreateTime())));
        holder.txtStaffName.setText(receipt.getStaffName());
        if (Integer.valueOf(receipt.getReceiptType()) == Const.TYPE_QR) {
            holder.imgReceipt.setImageResource(R.drawable.ic_code);
        } else {
            holder.imgReceipt.setImageResource(R.drawable.ic_phone);
        }
    }

    public void displayChildReceipt(ReceipsInformation receipsInformation, TextView txtTitle, TextView txtPrice) {
        String title = receipsInformation.getInvoiceTitle();
        if (TextUtils.isEmpty(title)) {
            title = "--";
        }
        if ("1".equals(receipsInformation.getStatus()) || "2".equals(receipsInformation.getStatus() )) { // chờ thanh toán
            String name = "[<font color='#31849B'>In progress</font>] - " + title;
            txtTitle.setText(Html.fromHtml(name), TextView.BufferType.SPANNABLE);
            String unit;// = receipsInformation.getCurrencyCode().equals(Const.VALUE_USD_CURRENCY_CODE) ? "$" : "KHR";
            String price_str;
            if (receipsInformation.getCurrencyCode().equals(Const.VALUE_USD_CURRENCY_CODE)) {
                unit = "$";
                price_str = DataUtils.getDoubleString(Double.parseDouble(receipsInformation.getTotalAmount())) + " " + unit;
            } else {
                unit = "KHR";
                price_str = DataUtils.formatterLong.format(Double.parseDouble(receipsInformation.getTotalAmount())) + " " + unit;
            }

            SpannableString price = new SpannableString(price_str);
            price.setSpan(new StrikethroughSpan(), 0, price.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            price.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.green_inprogress_paid)), 0, price.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            SpannableStringBuilder builder = new SpannableStringBuilder();
            builder.append(price);
            txtPrice.setText(builder);

        } else if ("3".equals(receipsInformation.getStatus())) { //  Phiếu thu đã thanh toán
            String name = title;
            txtTitle.setText(name);
            String unit;// = receipsInformation.getCurrencyCode().equals(Const.VALUE_USD_CURRENCY_CODE) ? "$" : "KHR";
            String price_str;
            if (receipsInformation.getCurrencyCode().equals(Const.VALUE_USD_CURRENCY_CODE)) {
                unit = "$";
                price_str = DataUtils.getDoubleString(Double.parseDouble(receipsInformation.getTotalAmount())) + " " + unit;
            } else {
                unit = "KHR";
                price_str = DataUtils.formatterLong.format(Double.parseDouble(receipsInformation.getTotalAmount())) + " " + unit;
            }
            txtPrice.setText(price_str);
        } else {  // phiếu thu lỗi
            String name = "[<font color='red'>Fail</font>] - " + title;
            txtTitle.setText(Html.fromHtml(name), TextView.BufferType.SPANNABLE);
            String unit;// = receipsInformation.getCurrencyCode().equals(Const.VALUE_USD_CURRENCY_CODE) ? "$" : "KHR";
            String price_str;
            if (receipsInformation.getCurrencyCode().equals(Const.VALUE_USD_CURRENCY_CODE)) {
                unit = "$";
                price_str = DataUtils.getDoubleString(Double.parseDouble(receipsInformation.getTotalAmount())) + " " + unit;
            } else {
                unit = "KHR";
                price_str = DataUtils.formatterLong.format(Double.parseDouble(receipsInformation.getTotalAmount())) + " " + unit;
            }
            SpannableString price = new SpannableString(price_str);
            price.setSpan(new StrikethroughSpan(), 0, price.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            price.setSpan(new ForegroundColorSpan(Color.RED), 0, price.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            SpannableStringBuilder builder = new SpannableStringBuilder();
            builder.append(price);
            txtPrice.setText(builder);
        }
    }

    @Override
    public int getItemCount() {
        return receiptList.size() > 0 ? receiptList.size() : 0;
    }

    class ReceiptsChildViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_receipt)
        ImageView imgReceipt;
        @BindView(R.id.txt_invoice_title)
        TextView txtInVoiceTitle;
        @BindView(R.id.txt_time)
        TextView txtTime;
        @BindView(R.id.txt_staff_name)
        TextView txtStaffName;
        @BindView(R.id.txt_money_total)
        TextView txtMoneyTotal;
        View itemview;

        public ReceiptsChildViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.itemview = itemView;
        }
    }
}
