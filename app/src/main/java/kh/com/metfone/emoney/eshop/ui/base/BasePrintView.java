package kh.com.metfone.emoney.eshop.ui.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.afollestad.materialdialogs.MaterialDialog;
import kh.com.metfone.emoney.eshop.MyApplication;
import kh.com.metfone.emoney.eshop.R;
import kh.com.metfone.emoney.eshop.consts.Const;
import kh.com.metfone.emoney.eshop.printer.BluetoothService;
import kh.com.metfone.emoney.eshop.printer.Command;
import kh.com.metfone.emoney.eshop.printer.PrintPicture;
import kh.com.metfone.emoney.eshop.ui.setting.ChoosePrinterDeviceDialog;
import kh.com.metfone.emoney.eshop.utils.AppUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * Created by DCV on 3/27/2018.
 */

public abstract class BasePrintView extends BaseDialogFragment {
    protected BluetoothAdapter mBluetoothAdapter = null;
    protected BluetoothService mService = null;
    protected String printerAddress;
    protected ChoosePrinterDeviceDialog choosePrinterDeviceDialog;
    protected MaterialDialog connectPrinterDialog;

    @Override
    protected void setupDialogTitle() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get local Bluetooth adapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        connectPrinterDialog = AppUtils.createConnectPrinterProgress(getContext(), getString(R.string.connect_to));
        mService = MyApplication.getmService();
        if (mService == null) {
            mService = new BluetoothService(getContext(), mHandler);
        }
        mService.setmHandler(mHandler);
        // Register for broadcasts on BluetoothAdapter state change
        IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        getActivity().registerReceiver(mReceiver, filter);
    }

    @Override
    public synchronized void onResume() {
        super.onResume();

        if (mService != null && mBluetoothAdapter.isEnabled()) {

            if (mService.getState() == BluetoothService.STATE_NONE) {
                // Start the Bluetooth services
                mService.start();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mService != null) {
            MyApplication.setmService(mService);
        }
    }

    protected void checkBluetoothEnable() {
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(
                    BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, Const.REQUEST_ENABLE_BT);
            // Otherwise, setup the session
        } else {
            if (TextUtils.isEmpty(printerAddress)) {
                if (choosePrinterDeviceDialog == null) {
                    choosePrinterDeviceDialog = new ChoosePrinterDeviceDialog(getContext(), getActivityComponent());
                }
                choosePrinterDeviceDialog.show();
                choosePrinterListener();
            } else {
                connectPrinter();
            }
        }

    }

    protected void connectPrinter() {
        if (mService.getState() == BluetoothService.STATE_CONNECTED) {
            sendDataToPrint();
        } else {
            if (BluetoothAdapter.checkBluetoothAddress(printerAddress)) {
                BluetoothDevice device = mBluetoothAdapter
                        .getRemoteDevice(printerAddress);
                // Attempt to connect to the device
                mService.connect(device);
                connectPrinterDialog.show();
            } else {
                notifyError(getString(R.string.unalbe_connect));
            }
        }
    }

    protected abstract void choosePrinterListener();

    protected abstract void sendDataToPrint();

    /****************************************************************************************************/
    @SuppressLint("HandlerLeak")
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (getContext() != null) {
                switch (msg.what) {
                    case BluetoothService.MESSAGE_STATE_CHANGE:
                        switch (msg.arg1) {
                            case BluetoothService.STATE_CONNECTED:
                                connectPrinterDialog.hide();
                                sendDataToPrint();
                                break;
                            case BluetoothService.STATE_CONNECTING:
                                break;
                            case BluetoothService.STATE_LISTEN:
                                break;
                            case BluetoothService.STATE_NONE:
                                break;
                        }
                        break;
                    case BluetoothService.MESSAGE_WRITE:
                        break;
                    case BluetoothService.MESSAGE_READ:
                        break;
                    case BluetoothService.MESSAGE_DEVICE_NAME:
                        break;
                    case BluetoothService.MESSAGE_TOAST:
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                notifyError(msg.getData().getString(BluetoothService.TOAST));
                            }
                        });
                        break;
                    case BluetoothService.MESSAGE_CONNECTION_LOST:
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                notifyError(getString(R.string.lost_connection));
                            }
                        });
                        break;
                    case BluetoothService.MESSAGE_UNABLE_CONNECT:     //无法连接设备
                        connectPrinterDialog.hide();
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                notifyError(getString(R.string.unalbe_connect));
                            }
                        });
                        break;
                }
            }
        }
    };

    @Override
    public void onDestroy() {
        getActivity().unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,
                        BluetoothAdapter.ERROR);
                switch (state) {
                    case BluetoothAdapter.STATE_OFF:
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        mService.stop();
                        break;
                    case BluetoothAdapter.STATE_ON:
                        if (mService != null) {

                            if (mService.getState() == BluetoothService.STATE_NONE) {
                                // Start the Bluetooth services
                                mService.start();
                            }
                        }
                        break;
                    case BluetoothAdapter.STATE_TURNING_ON:
                        break;
                }
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Const.REQUEST_ENABLE_BT: {
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    // Bluetooth is now enabled, so set up a session
                    if (TextUtils.isEmpty(printerAddress)) {
                        if (choosePrinterDeviceDialog == null) {
                            choosePrinterDeviceDialog = new ChoosePrinterDeviceDialog(getContext(), getActivityComponent());
                        }
                        choosePrinterDeviceDialog.show();
                        choosePrinterListener();
                    } else {
                        connectPrinter();
                    }
                } else {
                }
                break;
            }
        }
    }

    /**
     * 加载assets文件资源
     */
    protected Bitmap getImageFromAssetsFile(String fileName) {
        Bitmap image = null;
        AssetManager am = getResources().getAssets();
        try {
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;

    }

    /*
     *SendDataByte
     */
    protected void SendDataByte(byte[] data) {
        mService.write(data);
    }


    /*
     * SendDataString
     */
    protected void SendDataString(String data) {
        if (data.length() > 0) {
            try {
                mService.write(data.getBytes("GBK"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * 打印图片
     */
    protected void printLogo(){

        //	byte[] buffer = PrinterCommand.POS_Set_PrtInit();
        Bitmap mBitmap = getImageFromAssetsFile("logo_for_print.bmp");
        int nMode = 0;
        int nPaperWidth = 360;
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


}
