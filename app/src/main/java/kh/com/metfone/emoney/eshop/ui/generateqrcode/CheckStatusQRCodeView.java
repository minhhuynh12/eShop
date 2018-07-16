package kh.com.metfone.emoney.eshop.ui.generateqrcode;

import android.bluetooth.BluetoothDevice;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import kh.com.metfone.emoney.eshop.R;
import kh.com.metfone.emoney.eshop.consts.Const;
import kh.com.metfone.emoney.eshop.data.models.Receipt;
import kh.com.metfone.emoney.eshop.data.models.ReceiptInfo;
import kh.com.metfone.emoney.eshop.data.models.UserInformation;
import kh.com.metfone.emoney.eshop.printer.Command;
import kh.com.metfone.emoney.eshop.printer.PrintPicture;
import kh.com.metfone.emoney.eshop.printer.PrinterCommand;
import kh.com.metfone.emoney.eshop.ui.base.BasePrintView;
import kh.com.metfone.emoney.eshop.ui.home.HomeActivity;
import kh.com.metfone.emoney.eshop.ui.newreceipt.NewReceiptView;
import kh.com.metfone.emoney.eshop.ui.newreceipt.ViewReceiptView;
import kh.com.metfone.emoney.eshop.utils.AppUtils;
import kh.com.metfone.emoney.eshop.utils.DataUtils;
import kh.com.metfone.emoney.eshop.utils.DateUtils;

import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

/**
 * Created by DCV on 3/6/2018.
 */

public class CheckStatusQRCodeView extends BasePrintView implements CheckStatusQRCodeMvpView {
    private ReceiptInfo receiptInfo;
    @BindView(R.id.img_qr_code)
    ImageView imgQRCode;
    @BindView(R.id.txt_invoice_title)
    TextView txtInvoiceTitle;
    @BindView(R.id.txt_amount)
    TextView txtAmount;
    private Handler handler;
    @Inject
    CheckStatusQRCodePresenter checkStatusQRCodePresenter;
    private UserInformation userInformation;
    private CompositeDisposable compositeDisposable;
    public static int flaw;
    public static String money;

    public CheckStatusQRCodeView newInstance(ReceiptInfo receiptInfo, String money) {
        CheckStatusQRCodeView checkStatusQRCodeView = new CheckStatusQRCodeView();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Const.KEY_NEW_RECEIPT, receiptInfo);
        checkStatusQRCodeView.setArguments(bundle);
        this.money = money;

        return checkStatusQRCodeView;
    }

    public static CheckStatusQRCodeView newInstance(ReceiptInfo receiptInfo) {
        CheckStatusQRCodeView checkStatusQRCodeView = new CheckStatusQRCodeView();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Const.KEY_NEW_RECEIPT, receiptInfo);
        checkStatusQRCodeView.setArguments(bundle);

        return checkStatusQRCodeView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            receiptInfo = bundle.getParcelable(Const.KEY_NEW_RECEIPT);
        }
        handler = new Handler();
    }

    @Override
    protected void choosePrinterListener() {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(choosePrinterDeviceDialog.getChoosePrinterPublish().subscribe(new Consumer<BluetoothDevice>() {
            @Override
            public void accept(BluetoothDevice bluetoothDevice) throws Exception {
                checkStatusQRCodePresenter.savePrinterAddress(bluetoothDevice);
                printerAddress = bluetoothDevice.getAddress();
                connectPrinter();
            }
        }));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_check_status_qr_code, container, false);
        ButterKnife.bind(this, view);
        getActivityComponent().inject(this);
        checkStatusQRCodePresenter.attachView(this);
        initView();
        return view;
    }

    private void initView() {
        try {
            Bitmap bitmap = encodeAsBitmap(receiptInfo.getReceiptCode());
            imgQRCode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        txtInvoiceTitle.setText(TextUtils.isEmpty(receiptInfo.getInvoiceTitle()) ? "--" : receiptInfo.getInvoiceTitle());

//        if (money.isEmpty() || "".equals(money) ){
            if (receiptInfo.getPaymentCurrencyCode().equals(Const.VALUE_USD_CURRENCY_CODE)) {
                txtAmount.setText(DataUtils.getDoubleString(receiptInfo.getPaymentTotalAmount()) + " USD");
            } else {
                txtAmount.setText(DataUtils.formatterLong.format(receiptInfo.getPaymentTotalAmount()) + " KHR");
            }
//        }else {
//            if (flaw == 2){
//                txtAmount.setText(money + "KHR");
//                txtAmount.setText(money);
//            }else {
//                txtAmount.setText(money + "USD");
//            }
//        }

    }

    @OnClick({R.id.btn_check_status , R.id.btnPrintQR})
    public void onClickEvent(View view) {
        switch (view.getId()){
            case R.id.btn_check_status:
                if (AppUtils.isConnectivityAvailable(getContext())) {
                    checkStatusQRCodePresenter.checkStatus(handler, receiptInfo.getTransReceiptId(), false);
                } else {
                    notifyError(R.string.MSG_CM_NO_NETWORK_FOUND);
                }
                break;
            case R.id.btnPrintQR:
                checkBluetoothEnable();
                break;
        }

    }

    @Override
    protected void setupDialogTitle() {
    }
    private void setToolbar() {
        if (activity instanceof HomeActivity) {
            ((HomeActivity) activity).setToolbar(0, getString(R.string.new_));
            ((HomeActivity) activity).getToolbarAction().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    checkBluetoothEnable();
//                    getActivity().onBackPressed();
                    ((HomeActivity) activity).replaceFragment(NewReceiptView.newInstance() , R.id.container, false);
                }
            });
            ((HomeActivity) activity).getToolbarTitle().setText(userInformation.getShopInfor().getShopName());
            ((HomeActivity) activity).getToolbarTitle().setMaxWidth((int) AppUtils.convertDpToPixel(getContext(), getResources().getDimension(R.dimen._45sdp)));
            ((HomeActivity) activity).setTitleToolbarLine(true);

//            ((HomeActivity) activity).getBackAction().setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
////                    if (((HomeActivity) activity).isHaveBackAction) {
//                        activity.onBackPressed();
////                    }
//                }
//            });

        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        checkStatusQRCodePresenter.autoCheckStatus(handler, receiptInfo.getTransReceiptId());

    }


    private Bitmap encodeAsBitmap(String str) throws WriterException {
        BitMatrix result;
        try {
            result = new MultiFormatWriter().encode(str,
                    BarcodeFormat.QR_CODE, 300, 300, null);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int w = result.getWidth();
        int h = result.getHeight();
        int[] pixels = new int[w * h];
        for (int y = 0; y < h; y++) {
            int offset = y * w;
            for (int x = 0; x < w; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, 300, 0, 0, w, h);
        return bitmap;
    }

    @Override
    public void alreadyPaidSuccess(Receipt receipt) {
        if (activity instanceof HomeActivity) {
            ((HomeActivity) activity).replaceFragment(ViewReceiptView.newInstance(receipt.getReceiptInfo()), R.id.container, false);
            ((HomeActivity) activity).getToolbarTitle().setText(getString(R.string.receipt));
            ((HomeActivity) activity).setTitleToolbarLine(true);
        }
    }

    @Override
    public void showPaidStatus(String message) {
        notifyError(message);
    }

    @Override
    public void getUserInfoSuccess(UserInformation userInformation) {
        this.userInformation = userInformation;
        setToolbar();
    }

    @Override
    public void getPrinterAddressSuccess(String printerAddress) {
        this.printerAddress = printerAddress;
    }

    @Override
    protected void sendDataToPrint() {
        SendDataByte(Command.ESC_Init);
        SendDataByte(Command.LF);
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
        SendDataString((DateUtils.formatDateTime(new Date(receiptInfo.getCreateTime()), DateUtils.PATTERN_DD_MM_YYYY_HH_MM_SS)) + "\n");

//        byte[] qrcode = PrinterCommand.getBarCommand(receipt.getReceiptInfo().getReceiptCode(), 0, 3, 6);//
//        Command.ESC_Align[2] = 0x01;
//        SendDataByte(Command.ESC_Align);
//        SendDataByte(qrcode);
        printQRCode();

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

        // Total amount
        String amountTotalString;
//        if (receiptInfo.getCurrencyCode().equals(Const.VALUE_USD_CURRENCY_CODE)) {
//            amountTotalString = DataUtils.getDoubleString(Double.parseDouble(receiptInfo.getTotalAmount())) + " USD";
//        } else {
//            amountTotalString = DataUtils.formatterLong.format(Double.parseDouble(receiptInfo.getTotalAmount())) + " KHR";
//        }
        StringBuilder totalAmount = new StringBuilder("Total amount: ");
        space = 32 - money.length() - totalAmount.length();
        if (space > 0) {
            for (int i = 0; i < space; i++) {
                totalAmount.append(" ");
            }
        }
        totalAmount.append(money);
        totalAmount.append("\n\n         --------------         \n\n");
        Command.ESC_Align[2] = 0x00;
        SendDataByte(Command.ESC_Align);
        SendDataByte(Command.GS_ExclamationMark);
        SendDataByte(Command.GS_ExclamationMark);
        SendDataByte(PrinterCommand.POS_Set_LineSpace(10));
        SendDataString(totalAmount.toString());

        // end ticket

        Command.ESC_Align[2] = 0x01;
        SendDataByte(Command.ESC_Align);
        SendDataByte(Command.GS_ExclamationMark);
        SendDataByte(PrinterCommand.POS_Set_LineSpace(40));
        SendDataString(("Please use eMoney application to scan above QR Code and pay for your bill\nThank you\n\n\n"));

        SendDataByte(PrinterCommand.POS_Set_PrtAndFeedPaper(48));
        SendDataByte(Command.GS_V_m_n);
    }

    /*
     * 打印图片
     */
    protected void printQRCode() {

        //	byte[] buffer = PrinterCommand.POS_Set_PrtInit();
        Bitmap mBitmap = ((BitmapDrawable) imgQRCode.getDrawable()).getBitmap();
        int nMode = 0;
        int nPaperWidth = 384;
        if (mBitmap != null) {
            /**
             * Parameters:
             * mBitmap  要打印的图片
             * nWidth   打印宽度（58和80）
             * nMode    打印模式
             * Returns: byte[]
             */
            byte[] data = PrintPicture.POS_PrintBMP(mBitmap, nPaperWidth, nMode);
            //	SendDataByte(buffer);
            Command.ESC_Align[2] = 0x01;
            SendDataByte(Command.ESC_Align);
            SendDataByte(data);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onDestroy() {
        if (checkStatusQRCodePresenter != null) {
            checkStatusQRCodePresenter.detachView();
        }
        super.onDestroy();

    }
}
