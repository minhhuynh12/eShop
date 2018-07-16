package kh.com.metfone.emoney.eshop.ui.setting;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import kh.com.metfone.emoney.eshop.R;

/**
 * Created by DCV on 3/26/2018.
 */

public class PrinterAdapter extends ArrayAdapter<BluetoothDevice> {
    public PrinterAdapter(@NonNull Context context) {
        super(context, 0);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        BluetoothDevice bluetoothDevice = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.device_name, parent, false);
        }
        TextView tvName = (TextView) convertView.findViewById(R.id.txt_device_name);
        // Populate the data into the template view using the data object
        tvName.setText(bluetoothDevice.getName());
        return convertView;
    }
}
