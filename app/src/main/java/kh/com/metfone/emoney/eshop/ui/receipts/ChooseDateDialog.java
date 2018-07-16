package kh.com.metfone.emoney.eshop.ui.receipts;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import kh.com.metfone.emoney.eshop.R;
import kh.com.metfone.emoney.eshop.ui.base.BaseDialogActivity;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by DCV on 3/8/2018.
 */

public class ChooseDateDialog extends BaseDialogActivity implements DatePickerDialog.OnDateSetListener {
    private static final int DATE_DIALOG_ID = 999;
    private MaterialDialog dialog;
    Date fromDate;
    Date toDate;
    Calendar calendar;
    private boolean isChooseFromDate;
    @BindView(R.id.txt_from_date)
    TextView txtFromDate;
    @BindView(R.id.txt_to_date)
    TextView txtToDate;
    private Context context;
    private PublishSubject<Date> onChangeFromDate, onChangeToDate;

    public ChooseDateDialog(Context context, Date fromDate, Date toDate) {
        dialog = new MaterialDialog.Builder(context)
                .customView(R.layout.dialog_choose_date, false)
                .autoDismiss(false)
                .canceledOnTouchOutside(false)
                .show();
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.context = context;
        onChangeFromDate = PublishSubject.create();
        onChangeToDate = PublishSubject.create();
        calendar = Calendar.getInstance();
        View view = dialog.getCustomView();
        assert view != null;
        ButterKnife.bind(this, view);
        txtFromDate.setText(fromDate.getDate() + "/" + (fromDate.getMonth() + 1) + "/" + (toDate.getYear() + 1900));
        txtToDate.setText(toDate.getDate() + "/" + (toDate.getMonth() + 1) + "/" + (toDate.getYear() + 1900));
    }

    @Override
    protected void setupDialogTitle() {

    }

    @OnClick({R.id.txt_close, R.id.img_choose_from_date, R.id.img_choose_to_date, R.id.txt_select})
    public void onClickEvent(View view) {
        switch (view.getId()) {
            case R.id.txt_close:
                dialog.dismiss();
                break;
            case R.id.txt_select:
                onChangeFromDate.onNext(fromDate);
                onChangeToDate.onNext(toDate);
                dialog.dismiss();
                break;
            case R.id.img_choose_from_date:
                calendar.setTime(fromDate);
                isChooseFromDate = true;
                setDataCalendar();
                break;
            case R.id.img_choose_to_date:
                calendar.setTime(toDate);
                setDataCalendar();
                isChooseFromDate = false;
                break;
        }
    }

    public void show() {
        dialog.show();
    }

    private void setDataCalendar() {
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setMaxDate(Calendar.getInstance());
        dpd.show(((Activity) context).getFragmentManager(), ChooseDateDialog.class.getSimpleName());
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        if (isChooseFromDate) {
            txtFromDate.setText("" + dayOfMonth + "/" + "" + (monthOfYear + 1) + "/" + "" + year);
            fromDate.setYear(year -1900);
            fromDate.setMonth(monthOfYear);
            fromDate.setDate(dayOfMonth);
        } else {
            txtToDate.setText("" + dayOfMonth + "/" + "" + (monthOfYear + 1) + "/" + "" + year);
            toDate.setYear(year -1900);
            toDate.setMonth(monthOfYear);
            toDate.setDate(dayOfMonth);
        }
    }

    public PublishSubject<Date> getOnChangeFromDate() {
        return onChangeFromDate;
    }

    public PublishSubject<Date> getOnChangeToDate() {
        return onChangeToDate;
    }
}
