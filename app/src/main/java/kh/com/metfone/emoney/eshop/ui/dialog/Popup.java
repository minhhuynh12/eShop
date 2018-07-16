package kh.com.metfone.emoney.eshop.ui.dialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kh.com.metfone.emoney.eshop.R;

/**
 * Created by minhhc on 6/22/2018.
 */
public class Popup extends DialogFragment {

    private int viewResource;

    public Popup(int viewResource){
        this.viewResource = viewResource;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(viewResource, container, false);

        // Do all the stuff to initialize your custom view

        return v;
    }
}