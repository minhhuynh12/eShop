package kh.com.metfone.emoney.eshop.ui.confirmphone;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import kh.com.metfone.emoney.eshop.R;
import kh.com.metfone.emoney.eshop.consts.Const;
import kh.com.metfone.emoney.eshop.data.models.CommonConfigInfo;
import kh.com.metfone.emoney.eshop.data.models.Receipt;
import kh.com.metfone.emoney.eshop.data.models.ShopConfig;
import kh.com.metfone.emoney.eshop.data.models.UserInformation;
import kh.com.metfone.emoney.eshop.ui.base.BaseDialogFragment;
import kh.com.metfone.emoney.eshop.ui.home.HomeActivity;
import kh.com.metfone.emoney.eshop.utils.AppUtils;
import kh.com.metfone.emoney.eshop.utils.DataUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

import static kh.com.metfone.emoney.eshop.utils.DataUtils.formatterSmallDouble;

/**
 * Created by DCV on 3/6/2018.
 */

public class ConfirmByPhoneView extends BaseDialogFragment implements ConfirmByPhoneMvpView {
    @BindView(R.id.spn_switch_money_unit)
    Spinner spn_switch_money_unit;

    @BindView(R.id.spn_switch_discount)
    Spinner spn_switch_discount;

    @BindView(R.id.edt_amount)
    EditText edtAmount;

    @BindView(R.id.edt_discount)
    EditText edtDiscount;

    @BindView(R.id.txt_total_money)
    TextView txtTotalMoney;

    @BindView(R.id.cv_cannot_use_this_feature)
    CardView cvCannotUseThisFeature;

    @BindView(R.id.txt_cannot_this_feature)
    TextView txt_cannot_this_feature;

    @BindView(R.id.scr_content)
    ScrollView scr_content;

    @BindView(R.id.txt_total_money_change)
    TextView txt_total_money_change;

    @BindView(R.id.tvKHRAcleda)
    TextView tvKHRAcleda;

    @BindView(R.id.tvUsdAcleda)
    TextView tvUsdAcleda;

    private String monetaryUnit = "USD";
    private String discountUnit = "Percent";
    private double discountUsd;
    private long discountKhr;

    private double moneyTotalUsd;
    private long moneyTotalKhr;
    private int flawCurrency = 0;

    private String paymentCurrencyCode = "840";


    @BindView(R.id.txt_calculate_percent_discount)
    TextView txtCalculatePercentDiscount;
    @BindView(R.id.tvCurrency)
    TextView tvCurrency;

    @BindView(R.id.edt_invoice_title)
    EditText edt_invoice_title;

    @BindView(R.id.edt_customer_phone)
    EditText edt_customer_phone;

    @BindView(R.id.linearKHRAcleda)
    LinearLayout linearKHRAcleda;

    @BindView(R.id.linearUsdAcleda)
    LinearLayout linearUsdAcleda;

    private UserInformation userInformation;
    private String commonConfigValue;

    @Inject
    ConfirmByPhonePresenter confirmByPhonePresenter;

    public static ConfirmByPhoneView newInstance() {
        ConfirmByPhoneView confirmByPhoneView = new ConfirmByPhoneView();
        return confirmByPhoneView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_confirm_by_phone, container, false);
        ButterKnife.bind(this, view);
        getActivityComponent().inject(this);
        confirmByPhonePresenter.attachView(this);
        return view;
    }

    public void initView() {
        if (Const.VALUE_CHAIN_STORE_MANAGEMENT == userInformation.getUserInfo().getStaffType()) {
            cvCannotUseThisFeature.setVisibility(View.VISIBLE);
            scr_content.setVisibility(View.GONE);
            txt_cannot_this_feature.setText(getString(R.string.only_for_owner_or_staff));
        } else {
            if (userInformation.getShopInfor().getVerified() != Const.VERIFIED) {
                cvCannotUseThisFeature.setVisibility(View.VISIBLE);
                scr_content.setVisibility(View.GONE);
                txt_cannot_this_feature.setText(getString(R.string.UNVERIFY));
            } else {
                cvCannotUseThisFeature.setVisibility(View.GONE);
                scr_content.setVisibility(View.VISIBLE);
            }
        }
        String[] monetaryArray = getContext().getResources().getStringArray(R.array.monetary_unit_array);
        ArrayAdapter<String> adapterMonetaryUnit = new ArrayAdapter<String>(
                getActivity(),
                R.layout.item_spinner,
                monetaryArray
        );
        spn_switch_money_unit.setAdapter(adapterMonetaryUnit);

        String[] discountUnitArray = getContext().getResources().getStringArray(R.array.discount_unit_array);
        ArrayAdapter<String> adapterDisCountUnit = new ArrayAdapter<String>(
                getActivity(),
                R.layout.item_spinner,
                discountUnitArray
        );
        spn_switch_discount.setAdapter(adapterDisCountUnit);
    }


    private void convertAmount(String monetaryUnit) {
        if (monetaryUnit.equals(getString(R.string.usd))) {
            double money = DataUtils.convertKhrToUsd(Long.parseLong(edtAmount.getText().toString()), commonConfigValue);
            edtAmount.setText(String.valueOf(DataUtils.getDoubleString(money)));
        } else {
            long money = DataUtils.convertUsdToKhr(Double.parseDouble(edtAmount.getText().toString()), commonConfigValue);
            edtAmount.setText(String.valueOf(money));
        }
    }

    /*public void setAdapterForDiscountSpiner() {
        String[] discountUnitArray = getContext().getResources().getStringArray(R.array.discount_unit_array);
        ArrayAdapter<String> adapterDisCountUnit = new ArrayAdapter<String>(
                getActivity(),
                R.layout.item_spinner,
                discountUnitArray
        );
        spn_switch_discount.setAdapter(adapterDisCountUnit);
    }*/

    private void initEvent() {
        spn_switch_money_unit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               /* if (!((TextView) view).getText().toString().equals(monetaryUnit)) {
                    String[] discountUnitArray = getContext().getResources().getStringArray(R.array.discount_unit_array);

                    monetaryUnit = ((TextView) view).getText().toString();
                    convertAmount(((TextView) view).getText().toString());
                }*/

                if (!((TextView) view).getText().toString().equals(monetaryUnit)) {
                    monetaryUnit = ((TextView) view).getText().toString();
                    if (monetaryUnit.equals(getString(R.string.usd))) {

                        edtAmount.setHint("0.00");
                        edtAmount.setInputType(InputType.TYPE_CLASS_TEXT);
                        edtAmount.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                        if (flawCurrency == 0){
                            setColorView(linearUsdAcleda, R.drawable.boder_bg_red);
                            setColorView(linearKHRAcleda, R.drawable.boder_bg_while);
                            setColorView(tvUsdAcleda, R.color.colorWhite);
                            setColorView(tvKHRAcleda, R.color.colorRed);
                            paymentCurrencyCode = "840";
                        }

                    } else {
                        edtAmount.setHint("0");
                        edtAmount.setInputType(InputType.TYPE_CLASS_TEXT);
                        edtAmount.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
                        if (flawCurrency == 0){
                            setColorView(linearUsdAcleda, R.drawable.boder_bg_while);
                            setColorView(linearKHRAcleda, R.drawable.boder_bg_red);
                            setColorView(tvUsdAcleda, R.color.colorRed);
                            setColorView(tvKHRAcleda, R.color.colorWhite);
                            paymentCurrencyCode = "116";
                        }

                    }
                }
                calculateTotals();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spn_switch_discount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!((TextView) view).getText().toString().equals(discountUnit)) {
                    discountUnit = ((TextView) view).getText().toString();
                    convertDiscount(((TextView) view).getText().toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /*private void convertDiscount(String discountUnit) {
        calculateTotals();
    }
*/
    private void convertDiscount(String discountUnit) {
        if (discountUnit.equals(getString(R.string.percent))) {
            edtDiscount.setHint("0%");
            edtDiscount.setInputType(InputType.TYPE_CLASS_TEXT);
            edtDiscount.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        } else if (discountUnit.equals(getString(R.string.usd))) {
            edtDiscount.setHint("0.00");
            edtDiscount.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
            edtDiscount.setInputType(InputType.TYPE_CLASS_TEXT);
            edtDiscount.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        } else {
            edtDiscount.setHint("0");
            edtDiscount.setInputType(InputType.TYPE_CLASS_TEXT);
            edtDiscount.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
        }
        calculateTotals();
    }


    /**
     * calculate total money
     */
    private void calculateTotals() {
        String emptyDiscountHolder;
        tvCurrency.setText("1 USD = " + DataUtils.getLongString(Long.parseLong(commonConfigValue)) + " KHR");
        if (discountUnit.equals(getString(R.string.percent))) {
            if (checkIsNumber(edtDiscount.getText().toString())) {
                emptyDiscountHolder = edtDiscount.getText().toString();
            } else {
                emptyDiscountHolder = "0";
            }
            if (monetaryUnit.equals(getString(R.string.usd))) {
                    String strTotal = "";
                if (checkIsNumber(edtAmount.getText().toString())) {
                    if (checkIsNumber(edtDiscount.getText().toString()) && !edtDiscount.getText().toString().equals("%")) {
                        discountUsd = (Double.parseDouble(edtAmount.getText().toString()) / 100.0f) * Double.parseDouble(edtDiscount.getText().toString());
                    } else {
                        discountUsd = 0;
                    }
                    moneyTotalUsd = Double.parseDouble(edtAmount.getText().toString()) - discountUsd;
                    moneyTotalUsd = (double) Math.round(moneyTotalUsd*100)/100.00;

                } else {
                    discountUsd = 0;
                    moneyTotalUsd = 0;
                }
                txtCalculatePercentDiscount.setText(String.format(getResources().getString(R.string.calculate_percent_discount),
                        emptyDiscountHolder + "% ≈ " + DataUtils.getDoubleString(discountUsd) + "$"));
                txtTotalMoney.setText(DataUtils.getDoubleString(moneyTotalUsd) + " USD");
                switch (flawCurrency){
                    case 0:
                        txt_total_money_change.setText(DataUtils.getDoubleString(moneyTotalUsd) + " USD");
                        break;
                    case 1:
                        txt_total_money_change.setText(DataUtils.getDoubleString(moneyTotalUsd) + " USD");
                        break;
                    case 2:
                        txt_total_money_change.setText(DataUtils.getLongString(moneyTotalKhr) + " KHR");
                        break;
                }
                moneyTotalKhr = (long) (moneyTotalUsd * Double.parseDouble(commonConfigValue));
            } else {
                if (checkIsNumber(edtAmount.getText().toString()) && Double.parseDouble(edtAmount.getText().toString()) > 0) {
                    if (checkIsNumber(edtDiscount.getText().toString())) {
                        discountKhr = (long) ((Double.parseDouble(edtAmount.getText().toString()) / 100) * Double.parseDouble(edtDiscount.getText().toString()));
                    } else {
                        discountKhr = 0;
                    }
                    moneyTotalKhr = (long) Double.parseDouble(edtAmount.getText().toString()) - discountKhr;
                } else {
                    discountKhr = 0;
                    moneyTotalKhr = 0;
                }
                txtCalculatePercentDiscount.setText(String.format(getResources().getString(R.string.calculate_percent_discount),
                        emptyDiscountHolder + "% ≈ " + DataUtils.formatterLong.format(discountKhr) + "KHR"));
                txtTotalMoney.setText(DataUtils.getLongString(moneyTotalKhr) + " KHR");
                txt_total_money_change.setText(DataUtils.getLongString(moneyTotalKhr) + " KHR");
                moneyTotalUsd = moneyTotalKhr / Double.parseDouble(commonConfigValue);
                switch (flawCurrency){
                    case 0:
                        txt_total_money_change.setText(DataUtils.getLongString(moneyTotalKhr) + " KHR");
                        break;
                    case 1:
                        txt_total_money_change.setText(DataUtils.getDoubleString(moneyTotalUsd) + " USD");
                        break;
                    case 2:
                        txt_total_money_change.setText(DataUtils.getLongString(moneyTotalKhr) + " KHR");
                        break;
                }

            }
        }
        //usd
        if (discountUnit.equals(getString(R.string.usd))) {
            if (monetaryUnit.equals(getString(R.string.usd))) {
                if (checkIsNumber(edtAmount.getText().toString())) {
                    if (checkIsNumber(edtDiscount.getText().toString())) {
                        discountUsd = Double.parseDouble(edtDiscount.getText().toString());
                        moneyTotalUsd = Double.parseDouble(edtAmount.getText().toString()) - discountUsd;
                    } else {
                        discountUsd = 0;
                        moneyTotalUsd = Double.parseDouble(edtAmount.getText().toString());
                    }
                } else {
                    if (checkIsNumber(edtDiscount.getText().toString())) {
                        discountUsd = Double.parseDouble(edtDiscount.getText().toString());
                        moneyTotalUsd = 0 - discountUsd;
                    } else {
                        discountUsd = 0;
                        moneyTotalUsd = 0;
                    }
                }
                txtCalculatePercentDiscount.setText(String.format(getResources().getString(R.string.calculate_percent_discount),
                        DataUtils.getDoubleString(discountUsd) + " $"));
                txtTotalMoney.setText(DataUtils.getDoubleString(moneyTotalUsd) + " USD");
//                txt_total_money_change.setText(DataUtils.getDoubleString(moneyTotalUsd) + " USD");
                moneyTotalKhr = (long) (moneyTotalUsd * Double.parseDouble(commonConfigValue));
                switch (flawCurrency){
                    case 0:
                        txt_total_money_change.setText(DataUtils.getDoubleString(moneyTotalUsd) + " USD");
                        break;
                    case 1:
                        txt_total_money_change.setText(DataUtils.getDoubleString(moneyTotalUsd) + " USD");
                        break;
                    case 2:
                        txt_total_money_change.setText(DataUtils.getLongString(moneyTotalKhr) + " KHR");
                        break;
                }


            } else {
                if (checkIsNumber(edtAmount.getText().toString()) && Double.parseDouble(edtAmount.getText().toString()) > 0) {
                    if (checkIsNumber(edtDiscount.getText().toString())) {
                        discountKhr = DataUtils.convertUsdToKhr(Double.parseDouble(edtDiscount.getText().toString()), commonConfigValue);
                        moneyTotalKhr = (long) (Double.parseDouble(edtAmount.getText().toString()) - discountKhr);
                    } else {
                        discountKhr = 0;
                        moneyTotalKhr = (long) Double.parseDouble(edtAmount.getText().toString());
                    }
                } else {
                    if (checkIsNumber(edtDiscount.getText().toString())) {
                        discountKhr = DataUtils.convertUsdToKhr(Double.parseDouble(edtDiscount.getText().toString()), commonConfigValue);
                        moneyTotalKhr = 0 - discountKhr;
                    } else {
                        discountKhr = 0;
                        moneyTotalKhr = 0;
                    }
                }
                if (checkIsNumber(edtDiscount.getText().toString())) {
                    emptyDiscountHolder = edtDiscount.getText().toString();
                } else {
                    emptyDiscountHolder = "0.00";
                }
                txtCalculatePercentDiscount.setText(String.format(getResources().getString(R.string.calculate_percent_discount),
                        emptyDiscountHolder + "$ ≈ " + DataUtils.formatterLong.format(discountKhr) + "KHR"));
                txtTotalMoney.setText(DataUtils.getLongString(moneyTotalKhr) + " KHR");
//                txt_total_money_change.setText(DataUtils.getLongString(moneyTotalKhr) + " KHR");
                moneyTotalUsd = moneyTotalKhr / Double.parseDouble(commonConfigValue);
                switch (flawCurrency){
                    case 0:
                        txt_total_money_change.setText(DataUtils.getLongString(moneyTotalKhr) + " KHR");
                        break;
                    case 1:
                        txt_total_money_change.setText(DataUtils.getDoubleString(moneyTotalUsd) + " USD");
                        break;
                    case 2:
                        txt_total_money_change.setText(DataUtils.getLongString(moneyTotalKhr) + " KHR");
                        break;
                }
            }
        }

        // khr
        if (discountUnit.equals(getString(R.string.khr))) {
            if (monetaryUnit.equals(getString(R.string.khr))) {
                if (checkIsNumber(edtAmount.getText().toString())) {
                    if (checkIsNumber(edtDiscount.getText().toString())) {
                        discountKhr = (long) Double.parseDouble(edtDiscount.getText().toString());
                        moneyTotalKhr = (long) (Double.parseDouble(edtAmount.getText().toString()) - discountKhr);
                    } else {
                        discountKhr = 0;
                        moneyTotalKhr = (long) Double.parseDouble(edtAmount.getText().toString());
                    }
                } else {
                    if (checkIsNumber(edtDiscount.getText().toString())) {
                        discountKhr = (long) Double.parseDouble(edtDiscount.getText().toString());
                        moneyTotalKhr = 0 - discountKhr;
                    } else {
                        discountKhr = 0;
                        moneyTotalKhr = 0;
                    }
                }
                txtTotalMoney.setText(DataUtils.getLongString(moneyTotalKhr) + " KHR");
//                txt_total_money_change.setText(DataUtils.getLongString(moneyTotalKhr) + " KHR");
                moneyTotalUsd = moneyTotalKhr / Double.parseDouble(commonConfigValue);
                txtCalculatePercentDiscount.setText(String.format(getResources().getString(R.string.calculate_percent_discount), DataUtils.formatterLong.format(discountKhr) + " KHR"));

                switch (flawCurrency){
                    case 0:
                        txt_total_money_change.setText(DataUtils.getLongString(moneyTotalKhr) + " KHR");
                        break;
                    case 1:
                        txt_total_money_change.setText(DataUtils.getDoubleString(moneyTotalUsd) + " USD");
                        break;
                    case 2:
                        txt_total_money_change.setText(DataUtils.getLongString(moneyTotalKhr) + " KHR");
                        break;
                }


            } else {
                if (checkIsNumber(edtAmount.getText().toString()) && Double.parseDouble(edtAmount.getText().toString()) > 0) {
                    if (checkIsNumber(edtDiscount.getText().toString())) {
                        discountUsd = DataUtils.convertKhrToUsd((long) Double.parseDouble(edtDiscount.getText().toString()), commonConfigValue);
                        moneyTotalUsd = Double.parseDouble(edtAmount.getText().toString()) - discountUsd;
                    } else {
                        discountUsd = 0;
                        moneyTotalUsd = Double.parseDouble(edtAmount.getText().toString());
                    }

                } else {
                    if (checkIsNumber(edtDiscount.getText().toString())) {
                        discountUsd = DataUtils.convertKhrToUsd((long) Double.parseDouble(edtDiscount.getText().toString()), commonConfigValue);
                        moneyTotalUsd = 0 - discountUsd;
                    } else {
                        discountUsd = 0;
                        moneyTotalUsd = 0;
                    }
                }

                if (checkIsNumber(edtDiscount.getText().toString())) {
                    emptyDiscountHolder = edtDiscount.getText().toString();
                } else {
                    emptyDiscountHolder = "0";
                }
                txtCalculatePercentDiscount.setText(String.format(getResources().getString(R.string.calculate_percent_discount),
                        emptyDiscountHolder + "KHR ≈ " + DataUtils.getDoubleString(discountUsd) + "$"));
                txtTotalMoney.setText(DataUtils.getDoubleString(moneyTotalUsd) + " USD");
//                txt_total_money_change.setText(DataUtils.getDoubleString(moneyTotalUsd) + " USD");
                moneyTotalKhr = (long) ( (double) Math.round(moneyTotalUsd*100)/100.00d  * Double.parseDouble(commonConfigValue));

                switch (flawCurrency){
                    case 0:
                        txt_total_money_change.setText(DataUtils.getDoubleString(moneyTotalUsd) + " USD");
                        break;
                    case 1:
                        txt_total_money_change.setText(DataUtils.getDoubleString(moneyTotalUsd) + " USD");
                        break;
                    case 2:
                        txt_total_money_change.setText(DataUtils.getLongString(moneyTotalKhr) + " KHR");
                        break;
                }

            }
        }
    }

    private boolean checkIsNumber(String text) {
        if (TextUtils.isEmpty(text)) {
            return false;
        }
        if (text.equals(".")) {
            return false;
        }
        if (text.endsWith(".")) {
            return false;
        }
        return true;
    }

    @Override
    protected void setupDialogTitle() {

    }

    @OnClick({R.id.btn_send_confirm, R.id.linearUsdAcleda, R.id.linearKHRAcleda})
    public void onClickEvent(View view) {
        switch (view.getId()) {
            case R.id.btn_send_confirm:
                validateFields();
                break;
            case R.id.linearUsdAcleda:
                flawCurrency = 1;
                setColorView(linearKHRAcleda, R.drawable.boder_bg_while);
                setColorView(linearUsdAcleda, R.drawable.boder_bg_red);
                setColorView(tvKHRAcleda, R.color.colorRed);
                setColorView(tvUsdAcleda, R.color.colorWhite);
                paymentCurrencyCode = "840";
                txt_total_money_change.setText(DataUtils.getDoubleString(moneyTotalUsd) + " USD");

                break;
            case R.id.linearKHRAcleda:
                flawCurrency = 2;
                setColorView(linearKHRAcleda, R.drawable.boder_bg_red);
                setColorView(linearUsdAcleda, R.drawable.boder_bg_while);
                setColorView(tvKHRAcleda, R.color.colorWhite);
                setColorView(tvUsdAcleda, R.color.colorRed);
                paymentCurrencyCode = "116";
                txt_total_money_change.setText(DataUtils.getLongString(moneyTotalKhr) + " KHR");
                break;
        }
    }

    private void setColorView(Object ob, int r) {
        if (ob instanceof TextView) {
            TextView textView = (TextView) ob;
            textView.setTextColor(getActivity().getResources().getColor(r));
        } else if (ob instanceof LinearLayout) {
            LinearLayout linearLayout = (LinearLayout) ob;
            linearLayout.setBackgroundResource(r);
        }


    }

    private void validateFields() {
        if (edt_customer_phone.getText().toString().length() == 0) {
            notifyError(R.string.ERR_RQ_001_EMONEY_CUSTOMER);
            return;
        }
        if (edtAmount.getText().toString().length() == 0) {
            notifyError(R.string.ERR_IV_110_AMOUNT);
            return;
        }
        if (monetaryUnit.equals(getString(R.string.khr)) && edtAmount.getText().toString().contains(".")) {
            notifyError(R.string.ERR_IV_111_AMOUNT);
            return;
        }

        if (monetaryUnit.equals(getString(R.string.usd)) && Double.parseDouble(edtAmount.getText().toString()) < 0.1) {
            notifyError(R.string.ERR_IV_110_AMOUNT);
            return;
        }
        if (monetaryUnit.equals(getString(R.string.khr)) && Double.parseDouble(edtAmount.getText().toString()) < 100) {
            notifyError(R.string.ERR_IV_110_AMOUNT);
            return;
        }
        if (discountUnit.equals(getString(R.string.khr))
                && edtDiscount.getText().toString().contains(".")) {
            notifyError(R.string.ERR_IV_110_DISCOUNT);
            return;
        }
        if (discountUnit.equals(getString(R.string.percent))
                && checkIsNumber(edtDiscount.getText().toString())
                && Double.parseDouble(edtDiscount.getText().toString()) > 100) {
            notifyError(R.string.ERR_IV_111_DISCOUNT);
            return;
        }

        if (monetaryUnit.equals(getString(R.string.usd))
                && discountUsd > Double.parseDouble(edtAmount.getText().toString())) {
            notifyError(R.string.ERR_IV_111_DISCOUNT);
            return;
        }
        if (monetaryUnit.equals(getString(R.string.khr))
                && discountKhr > Double.parseDouble(edtAmount.getText().toString())) {
            notifyError(R.string.ERR_IV_111_DISCOUNT);
            return;
        }

        if (monetaryUnit.equals(getString(R.string.usd)) && moneyTotalUsd < 0.1) {
            notifyError(R.string.ERR_IV_112_TOTAL_AMOUNT);
            return;
        }
        if (monetaryUnit.equals(getString(R.string.khr)) && moneyTotalKhr < 100) {
            notifyError(R.string.ERR_IV_112_TOTAL_AMOUNT);
            return;
        }

        if (AppUtils.isConnectivityAvailable(getContext())) {
            String discountText = "0";
            if (checkIsNumber(edtDiscount.getText().toString())) {
                discountText = edtDiscount.getText().toString();
            } else {
                discountText = "0";
            }
            confirmByPhonePresenter.newReceipt(userInformation.getUserInfo().getStaffCode(), "2", edt_invoice_title.getText().toString(), edt_customer_phone.getText().toString(),
                    DataUtils.getCurrencyCode(getContext(), monetaryUnit),
                    edtAmount.getText().toString(),
                    DataUtils.getCurrencyCode(getContext(), discountUnit),
                    discountText, paymentCurrencyCode);
        } else {
            notifyError(R.string.MSG_CM_NO_NETWORK_FOUND);
        }
    }

    @Override
    public void onDestroy() {
        if (confirmByPhonePresenter != null) {
            confirmByPhonePresenter.detachView();
        }
        super.onDestroy();
    }

    @OnTextChanged(R.id.edt_amount)
    public void onAmountTextChange(CharSequence text) {
        if (text.toString().equals("0") || text.toString().equals("0.00")) {
            edtAmount.setSelectAllOnFocus(true);
        } else {
            edtAmount.setSelectAllOnFocus(false);
        }
        calculateTotals();

    }

    @OnTextChanged(R.id.edt_discount)
    public void onDiscountTextChange(CharSequence text) {
        calculateTotals();
    }


    @Override
    public void getConfigSuccess(CommonConfigInfo commonConfigInfo) {
    }

    @Override
    public void getConfigFailed(String messageError) {

    }

    @Override
    public void getConfigSuccess(ShopConfig shopConfig) {
        this.commonConfigValue = shopConfig.getConfigValue();
    }

    @Override
    public void getUserInfoSuccess(UserInformation userInformation) {
        this.userInformation = userInformation;
        if (activity instanceof HomeActivity) {
            ((HomeActivity) activity).getToolbarTitle().setText(userInformation.getShopInfor().getShopName());
            ((HomeActivity) activity).setTitleToolbarLine(true);
            ((HomeActivity) activity).getToolbarTitle().setMaxWidth((int) AppUtils.convertDpToPixel(getContext(), getResources().getDimension(R.dimen._45sdp)));
        }
        initView();
        initEvent();
    }

    @Override
    public void newReceiptPhoneSuccess(Receipt receipt) {
        ((HomeActivity) activity).replaceFragment(CheckStatusUSSDView.newInstance(receipt, userInformation, edt_customer_phone.getText().toString()), R.id.container, false);
    }

    @Override
    public void newReceiptPhoneFailed(String messageError) {

    }
}
