package kh.com.metfone.emoney.eshop.ui.confirmphone;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import kh.com.metfone.emoney.eshop.R;
import kh.com.metfone.emoney.eshop.consts.Const;
import kh.com.metfone.emoney.eshop.data.models.Receipt;
import kh.com.metfone.emoney.eshop.data.models.UserInformation;
import kh.com.metfone.emoney.eshop.ui.base.BaseDialogFragment;
import kh.com.metfone.emoney.eshop.ui.home.HomeActivity;
import kh.com.metfone.emoney.eshop.ui.newreceipt.ViewReceiptView;
import kh.com.metfone.emoney.eshop.utils.AppUtils;
import kh.com.metfone.emoney.eshop.utils.DataUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by DCV on 3/6/2018.
 */

public class CheckStatusUSSDView extends BaseDialogFragment implements CheckStatusUSSDMvpView {
    private Receipt receipt;
    @BindView(R.id.btn_check_status)
    Button btn_check_status;
    @BindView(R.id.txt_invoice_title)
    TextView txt_invoice_title;
    @BindView(R.id.txt_amount)
    TextView txt_amount;
    @BindView(R.id.txt_customer_phone)
    TextView txt_customer_phone;

    @Inject
    CheckStatusUSSDPresenter checkUSSDPresenter;
    private UserInformation userInformation;
    private Handler handler;
    private static String customer_phone;
    private static String amount;

    public static CheckStatusUSSDView newInstance(Receipt receipt, UserInformation userInformation, String custom_phone) {
        customer_phone = custom_phone;
        CheckStatusUSSDView checkUSSDView = new CheckStatusUSSDView();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Const.KEY_NEW_RECEIPT, receipt);
        bundle.putParcelable(Const.KEY_USER_INFORMATION, userInformation);
        checkUSSDView.setArguments(bundle);
        return checkUSSDView;
    }

    public static CheckStatusUSSDView newInstance(Receipt receipt, UserInformation userInformation, String custom_phone , String amount_) {
        customer_phone = custom_phone;
        amount = amount_;
        CheckStatusUSSDView checkUSSDView = new CheckStatusUSSDView();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Const.KEY_NEW_RECEIPT, receipt);
        bundle.putParcelable(Const.KEY_USER_INFORMATION, userInformation);
        checkUSSDView.setArguments(bundle);
        return checkUSSDView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            receipt = bundle.getParcelable(Const.KEY_NEW_RECEIPT);
            userInformation = bundle.getParcelable(Const.KEY_USER_INFORMATION);
        }


        handler = new Handler();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        checkUSSDPresenter.confirmByPhone(userInformation.getUserInfo().getStaffCode(), handler, receipt.getReceiptInfo().getTransReceiptId(), true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_check_status_ussd, container, false);
        ButterKnife.bind(this, view);
        getActivityComponent().inject(this);
        checkUSSDPresenter.attachView(this);
//        checkUSSDPresenter.confirmByPhone(userInformation.getUserInfo().getStaffCode(), handler, receipt.getReceiptInfo().getTransReceiptId(), true);
//        checkUSSDPresenter.autoCheckStatus(handler, userInformation.getUserInfo().getStaffCode(), receipt.getReceiptInfo().getTransReceiptId());
        initView();


        return view;
    }

    public void initView() {
        txt_invoice_title.setText(TextUtils.isEmpty(receipt.getReceiptInfo().getInvoiceTitle()) ? "--" : receipt.getReceiptInfo().getInvoiceTitle());
        if (receipt.getReceiptInfo().getPaymentCurrencyCode().equals(Const.VALUE_USD_CURRENCY_CODE)) {
            txt_amount.setText(DataUtils.getDoubleString(receipt.getReceiptInfo().getPaymentTotalAmount()) + " USD");
        } else {
//            txt_amount.setText(DataUtils.formatterLong.format(Double.parseDouble(receipt.getReceiptInfo().getTotalAmount())) + " KHR");
            txt_amount.setText(DataUtils.formatterLong.format(receipt.getReceiptInfo().getPaymentTotalAmount()) + " KHR");
        }

        txt_customer_phone.setText(customer_phone);
    }

    @OnClick(R.id.btn_check_status)
    public void onClickEvent() {
        if (AppUtils.isConnectivityAvailable(getContext())) {
            checkUSSDPresenter.checkStatus(userInformation.getUserInfo().getStaffCode(), handler, receipt.getReceiptInfo().getTransReceiptId(), false);
        } else {
            notifyError(R.string.MSG_CM_NO_NETWORK_FOUND);
        }
    }

    @Override
    protected void setupDialogTitle() {
        if (activity instanceof HomeActivity) {
            ((HomeActivity) activity).getToolbarTitle().setText(userInformation.getShopInfor().getShopName());
            ((HomeActivity) activity).getToolbarTitle().setMaxWidth((int) AppUtils.convertDpToPixel(getContext(), getResources().getDimension(R.dimen._45sdp)));
            ((HomeActivity) activity).setTitleToolbarLine(true);
        }
    }

    @Override
    public void alreadyUSSDSuccess(Receipt receipt) {
        if (activity instanceof HomeActivity) {
            Receipt receipt1 = new Receipt();
            receipt1.setMoneyTotal(receipt.getReceiptInfo().getTotalAmount());
            receipt1.setReceiptInfo(receipt.getReceiptInfo());
            receipt1.setInvoiceTitle(receipt.getReceiptInfo().getInvoiceTitle());
            ((HomeActivity) activity).replaceFragment(ViewReceiptView.newInstance(receipt1.getReceiptInfo()), R.id.container, false);
        }

    }

    @Override
    public void showPaidStatus(String message) {
        notifyError(message);
    }

    @Override
    public void getUserInfoSuccess(UserInformation userInformation) {
        this.userInformation = userInformation;
        setupDialogTitle();
    }

    @Override
    public void onDetach() {
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        handler = null;
        super.onDestroy();
    }
}
