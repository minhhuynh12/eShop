package kh.com.metfone.emoney.eshop.ui.newreceipt;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import kh.com.metfone.emoney.eshop.R;
import kh.com.metfone.emoney.eshop.consts.Const;
import kh.com.metfone.emoney.eshop.data.domain.CheckStatusPaymentUseCase;
import kh.com.metfone.emoney.eshop.data.domain.ClientLogUseCase;
import kh.com.metfone.emoney.eshop.data.domain.GetAllReportUseCase;
import kh.com.metfone.emoney.eshop.data.domain.GetUserInforUseCase;
import kh.com.metfone.emoney.eshop.data.domain.LogoutUseCase;
import kh.com.metfone.emoney.eshop.data.models.ReceiptInfo;
import kh.com.metfone.emoney.eshop.data.models.UserInformation;
import kh.com.metfone.emoney.eshop.printer.Command;
import kh.com.metfone.emoney.eshop.printer.PrinterCommand;
import kh.com.metfone.emoney.eshop.ui.base.BasePrintView;
import kh.com.metfone.emoney.eshop.ui.base.BaseSaveLogCheckAuthenPresenter;
import kh.com.metfone.emoney.eshop.ui.home.HomeActivity;
import kh.com.metfone.emoney.eshop.ui.reports.ReportsMvpView;
import kh.com.metfone.emoney.eshop.utils.DataUtils;
import kh.com.metfone.emoney.eshop.utils.DateUtils;

import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import kh.com.metfone.emoney.eshop.utils.SharePreferenceHelper;

/**
 * Created by DCV on 3/15/2018.
 */

public class ViewReceiptView extends BasePrintView implements ViewReceiptMvpView {
    @BindView(R.id.txt_shop_name)
    TextView txtShopName;
    @BindView(R.id.txt_paid_time)
    TextView txtPaidTime;
    @BindView(R.id.txt_tid)
    TextView txtTID;
    @BindView(R.id.txt_invoice_title)
    TextView txtInvoiceTitle;
    @BindView(R.id.txt_amount)
    TextView txtAmount;
    @BindView(R.id.txt_discount)
    TextView txtDiscount;
    @BindView(R.id.txt_total_amount)
    TextView txtTotalAmount;
    @BindView(R.id.tvTotalAmountUSD)
    TextView tvTotalAmountUSD;
    @BindView(R.id.tvTotalAmountKHR)
    TextView tvTotalAmountKHR;
    @BindView(R.id.tvTip)
    TextView tvTip;

    private ReceiptInfo receiptInfo;
    private String discountString;
    private String amountString;
    private String amountTotalString;
    private String totalAmountUSD;
    private String totalAmountKHR;
    private String correntcyCode;
    private String discuntPrint;


    @Inject
    ViewReceiptPresenter viewReceiptPresenter;
    private UserInformation userInformation;

    private CompositeDisposable compositeDisposable;
    private boolean isAreadyShow;

    public ViewReceiptView() {
    }

    public static ViewReceiptView newInstance(ReceiptInfo receiptInfo) {
        ViewReceiptView viewReceiptView = new ViewReceiptView();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Const.KEY_NEW_RECEIPT, receiptInfo);
        viewReceiptView.setArguments(bundle);
        return viewReceiptView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            receiptInfo = bundle.getParcelable(Const.KEY_NEW_RECEIPT);
        }
    }

    @Override
    public synchronized void onResume() {
        super.onResume();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_receipt, container, false);
        ButterKnife.bind(this, view);
        getActivityComponent().inject(this);
        viewReceiptPresenter.attachView(this);
        unitView();
        return view;
    }

    private void unitView() {
        if (!isAreadyShow) {
            isAreadyShow = true;
            notifySuccess(getString(R.string.MSG_PAID_SUCCESS));
        }


        txtPaidTime.setText(DateUtils.formatDateTime(new Date(receiptInfo.getPaidTime()), DateUtils.PATTERN_DD_MM_YYYY_HH_MM_SS));
        if (TextUtils.isEmpty(receiptInfo.getInvoiceTitle())) {
            receiptInfo.setInvoiceTitle("--");
        }
        txtInvoiceTitle.setText(receiptInfo.getInvoiceTitle());
        if (Const.VALUE_USD_CURRENCY_CODE.equals(receiptInfo.getCurrencyCode())) {
            amountString = DataUtils.getDoubleString(Double.parseDouble(receiptInfo.getAmount())) + " USD";
//            amountTotalString = DataUtils.getDoubleString(Double.parseDouble(receiptInfo.getTotalAmount())) + " USD";
            txtAmount.setText(amountString);
//            txtTotalAmount.setText(amountTotalString);

        } else {
            amountString = DataUtils.formatterLong.format(Double.parseDouble(receiptInfo.getAmount())) + " KHR";
//            amountTotalString = DataUtils.formatterLong.format(Double.parseDouble(receiptInfo.getTotalAmount())) + " KHR";
            txtAmount.setText(amountString);
//            txtTotalAmount.setText(amountTotalString);
        }
        discountString = DataUtils.calculatorDiscount(receiptInfo.getCurrencyCode(),
                receiptInfo.getDiscountType(),
                receiptInfo.getDiscount(),
                receiptInfo.getDiscountAmount());
        txtDiscount.setText(discountString);
        discuntPrint = DataUtils.calculatorDiscountForPrint(receiptInfo.getCurrencyCode(),
                receiptInfo.getDiscountType(),
                receiptInfo.getDiscount(),
                receiptInfo.getDiscountAmount());
        txtTID.setText(receiptInfo.getPaidTid());
        correntcyCode = receiptInfo.getCurrencyCode();
        switch (correntcyCode) {
            case Const.VALUE_USD_CURRENCY_CODE:
                totalAmountUSD = DataUtils.getDoubleString(Double.parseDouble(receiptInfo.getTotalAmount())) + " USD";
                totalAmountKHR = DataUtils.formatterLong.format(Math.round(Double.parseDouble(receiptInfo.getTotalAmountConvert()))) + " KHR";
                // totalAmountKHR = receiptInfo.getTotalAmountConvert() + "KHR";
                tvTotalAmountUSD.setText(totalAmountUSD);
                tvTotalAmountKHR.setText(totalAmountKHR);
                break;
            case Const.VALUE_KHR_CURRENCY_CODE:
                totalAmountUSD = DataUtils.getDoubleString(Double.parseDouble(receiptInfo.getTotalAmountConvert())) + " USD";
                totalAmountKHR = DataUtils.formatterLong.format(Math.round(Double.parseDouble(receiptInfo.getTotalAmount()))) + " KHR";
                tvTotalAmountUSD.setText(totalAmountUSD);
                tvTotalAmountKHR.setText(totalAmountKHR);
                break;
        }

        if (receiptInfo.getPaymentTip() != null) {
            switch (receiptInfo.getPaymentCurrencyCode()) {
                case Const.VALUE_USD_CURRENCY_CODE:
                    tvTip.setText(DataUtils.getDoubleString(Double.parseDouble(receiptInfo.getPaymentTip())) + " USD");
//                    amountTotalString = DataUtils.getDoubleString(Double.parseDouble(receiptInfo.getPaidAmount())) + " USD";
//                    txtTotalAmount.setText(amountTotalString);
                    break;
                case Const.VALUE_KHR_CURRENCY_CODE:
                    tvTip.setText(DataUtils.getLongString(Math.round(Double.parseDouble(receiptInfo.getPaymentTip()))) + " KHR");
//                    amountTotalString = DataUtils.getLongString(Math.round(Double.parseDouble(receiptInfo.getPaidAmount()))) + " KHR";
//                    txtTotalAmount.setText(amountTotalString);
                    break;
            }
        }

        if (Const.VALUE_USD_CURRENCY_CODE.equals(receiptInfo.getPaymentCurrencyCode())){
            amountTotalString = DataUtils.getDoubleString(Double.parseDouble(receiptInfo.getPaidAmount())) + " USD";
            txtTotalAmount.setText(amountTotalString);
        }else {
            amountTotalString = DataUtils.getLongString(Math.round(Double.parseDouble(receiptInfo.getPaidAmount()))) + " KHR";
            txtTotalAmount.setText(amountTotalString);
        }




    }


    @Override
    protected void setupDialogTitle() {
        if (activity instanceof HomeActivity) {
            ((HomeActivity) activity).setToolbar(0, getString(R.string.done_action));
            ((HomeActivity) activity).getToolbarTitle().setText(R.string.receipt);
            ((HomeActivity) activity).getToolbarAction().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((HomeActivity) activity).getToolbarAction().setVisibility(View.GONE);
                    ((HomeActivity) activity).replaceFragment(NewReceiptView.newInstance(), R.id.container, false);
                }
            });
        }
    }

    @OnClick(R.id.btn_print_receipt)
    public void onClick() {
        // If Bluetooth is not on, request that it be enabled.
        // setupChat() will then be called during onActivityResult
        checkBluetoothEnable();

    }


    protected void choosePrinterListener() {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(choosePrinterDeviceDialog.getChoosePrinterPublish().subscribe(new Consumer<BluetoothDevice>() {
            @Override
            public void accept(BluetoothDevice bluetoothDevice) throws Exception {
                viewReceiptPresenter.savePrinterAddress(bluetoothDevice);
                printerAddress = bluetoothDevice.getAddress();
                connectPrinter();
            }
        }));
    }

    @Override
    public void onDestroy() {
        if (viewReceiptPresenter != null) {
            viewReceiptPresenter.detachView();
        }
        super.onDestroy();
    }

    @Override
    public void getPrinterAddressSuccess(String printerAddress) {
        this.printerAddress = printerAddress;
    }

    @Override
    public void getUserInfoSuccess(UserInformation userInformation) {
        this.userInformation = userInformation;
        txtShopName.setText(userInformation.getShopInfor().getShopName());
    }

    @Override
    public void getUserLogin(String username) {

    }


    protected void sendDataToPrint() {
        SendDataByte(Command.ESC_Init);
        SendDataByte(Command.LF);
        printLogo();
        Print_Ex();
        printLogo();
        Print_Ex();
    }

    /**
     * 打印自定义小票
     */
    private void Print_Ex() {
        int space;
        SendDataString("\n");

        // shop name
        Command.ESC_Align[2] = 0x01;
        SendDataByte(Command.ESC_Align);
        Command.GS_ExclamationMark[2] = 0x11;
        SendDataByte(Command.GS_ExclamationMark);
        SendDataString(("\n" + userInformation.getShopInfor().getShopName() + "\n\n"));

        // create date
        SendDataByte(Command.ESC_Align);
        Command.GS_ExclamationMark[2] = 0x00;
        SendDataByte(Command.GS_ExclamationMark);
        SendDataString((DateUtils.formatDateTime(new Date(receiptInfo.getPaidTime()), DateUtils.PATTERN_DD_MM_YYYY_HH_MM_SS) + "\n\n"));

        // recepit
        StringBuilder recepit = new StringBuilder("Recepit");
        SendDataByte(Command.ESC_Align);
        Command.GS_ExclamationMark[2] = 0x00;
        SendDataByte(Command.GS_ExclamationMark);
        recepit.append(" #");
        recepit.append(receiptInfo.getTransReceiptId());
        recepit.append("\n");

        SendDataString(recepit.toString() + "\n");

        //cashier
        StringBuilder user = new StringBuilder("Cashier: ");
        space = 32 - userInformation.getUserInfo().getStaffName().length() - user.length();
        if (space > 0) {
            for (int i = 0; i < space; i++) {
                user.append(" ");
            }
        }
        user.append(userInformation.getUserInfo().getStaffName());
        user.append("\n");
        Command.ESC_Align[2] = 0x00;
        SendDataByte(Command.ESC_Align);
        SendDataByte(Command.GS_ExclamationMark);
        SendDataString(user.toString());

        // invoice title
        StringBuilder invoiceTitle = new StringBuilder("Invoice number: ");
        if (TextUtils.isEmpty(receiptInfo.getInvoiceTitle())) {
            space = 32 - 2 - invoiceTitle.length();
        } else {
            space = 32 - receiptInfo.getInvoiceTitle().length() - invoiceTitle.length();
        }
        if (space > 0) {
            for (int i = 0; i < space; i++) {
                invoiceTitle.append(" ");
            }
        }
        invoiceTitle.append(TextUtils.isEmpty(receiptInfo.getInvoiceTitle()) ? "--" : receiptInfo.getInvoiceTitle());
        invoiceTitle.append("\n");

        Command.ESC_Align[2] = 0x00;
        SendDataByte(Command.ESC_Align);
        SendDataByte(Command.GS_ExclamationMark);
        SendDataString(invoiceTitle.toString());

        // tid
        StringBuilder tid = new StringBuilder("TID: ");
        space = 32 - receiptInfo.getPaidTid().length() - tid.length();
        if (space > 0) {
            for (int i = 0; i < space; i++) {
                tid.append(" ");
            }
        }
        tid.append(receiptInfo.getPaidTid());
        tid.append("\n");
        Command.ESC_Align[2] = 0x00;
        SendDataByte(Command.ESC_Align);
        SendDataByte(Command.GS_ExclamationMark);
        SendDataString(tid.toString());

        // amount
        StringBuilder amount = new StringBuilder("Amount: ");
        space = 32 - amountString.length() - amount.length();
        if (space > 0) {
            for (int i = 0; i < space; i++) {
                amount.append(" ");
            }
        }
        amount.append(amountString);
        amount.append("\n");
        Command.ESC_Align[2] = 0x00;
        SendDataByte(Command.ESC_Align);
        SendDataByte(Command.GS_ExclamationMark);
        SendDataString(amount.toString());

        // discount
        StringBuilder discount = new StringBuilder("Discount: ");
        space = 32 - discuntPrint.length() - discount.length();
        if (space > 0) {
            for (int i = 0; i < space; i++) {
                discount.append(" ");
            }
        }
        discount.append(discuntPrint);
        discount.append("");
        Command.ESC_Align[2] = 0x00;
        SendDataByte(Command.ESC_Align);
        SendDataByte(Command.GS_ExclamationMark);
        SendDataString(discount.toString());

        //total amount usd

        StringBuilder strTotalUSD = new StringBuilder("Total amount(USD): ");
        space = 32 - totalAmountUSD.length() - strTotalUSD.length();
        if (space > 0) {
            for (int i = 0; i < space; i++) {
                strTotalUSD.append(" ");
            }
        }
        strTotalUSD.append(totalAmountUSD);
        strTotalUSD.append("\n");
        Command.ESC_Align[2] = 0x00;
        SendDataByte(Command.ESC_Align);
        SendDataByte(Command.GS_ExclamationMark);
        SendDataString("\n--------------------------------\n");
        SendDataString(strTotalUSD.toString());

        //total amount khr

        StringBuilder strTotalKHR = new StringBuilder("Total amount(KHR): ");
        space = 32 - totalAmountKHR.length() - strTotalKHR.length();
        if (space > 0) {
            for (int i = 0; i < space; i++) {
                strTotalKHR.append(" ");
            }
        }
        strTotalKHR.append(totalAmountKHR);
        strTotalKHR.append("\n");
        Command.ESC_Align[2] = 0x00;
        SendDataByte(Command.ESC_Align);
        SendDataByte(Command.GS_ExclamationMark);
        SendDataString(strTotalKHR.toString());

//      tip
        StringBuilder strTip = new StringBuilder("Tip: ");
        space = 32 - tvTip.getText().toString().length() - strTip.length();
        if (space > 0) {
            for (int i = 0; i < space; i++) {
                strTip.append(" ");
            }
        }
        strTip.append(tvTip.getText().toString());
        strTip.append("\n");
        Command.ESC_Align[2] = 0x00;
        SendDataByte(Command.ESC_Align);
        SendDataByte(Command.GS_ExclamationMark);
        SendDataByte(PrinterCommand.POS_Set_LineSpace(10));
        SendDataString(strTip.toString());


        // Total amount

        StringBuilder totalAmount = new StringBuilder("Paid amount: ");
        space = 32 - amountTotalString.length() - totalAmount.length();
        if (space > 0) {
            for (int i = 0; i < space; i++) {
                totalAmount.append(" ");
            }
        }
        totalAmount.append(amountTotalString);
        totalAmount.append("\n\n--------------------------------\n\n");
        Command.ESC_Align[2] = 0x00;
        SendDataByte(Command.ESC_Align);
        SendDataByte(Command.GS_ExclamationMark);
        SendDataByte(Command.GS_ExclamationMark);
        SendDataByte(PrinterCommand.POS_Set_LineSpace(10));
        SendDataString("\n\n--------------------------------\n\n");
        SendDataString(totalAmount.toString());

        // end ticket

        Command.ESC_Align[2] = 0x01;
        SendDataByte(Command.ESC_Align);
        SendDataByte(Command.GS_ExclamationMark);
        SendDataByte(PrinterCommand.POS_Set_LineSpace(40));
        SendDataString(("Thank you for using eMoney\n\n\n"));

        SendDataByte(PrinterCommand.POS_Set_PrtAndFeedPaper(48));
        SendDataByte(Command.GS_V_m_n);
    }
}
