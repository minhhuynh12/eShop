package kh.com.metfone.emoney.eshop.ui.newreceipt;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kh.com.metfone.emoney.eshop.R;
import kh.com.metfone.emoney.eshop.ui.base.BaseDialogFragment;
import kh.com.metfone.emoney.eshop.ui.confirmphone.ConfirmByPhoneView;
import kh.com.metfone.emoney.eshop.ui.generateqrcode.GenerateQRCodeView;
import kh.com.metfone.emoney.eshop.ui.home.HomeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by DCV on 3/2/2018.
 */

public class NewReceiptView extends BaseDialogFragment implements NewReceiptMvpView {

    @BindView(R.id.view_pager)
    public ViewPager viewPager;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    private int positionPage;
    private GenerateQRCodeView generateQRCodeView;
    private ConfirmByPhoneView confirmByPhoneView;

    public static NewReceiptView newInstance() {
        NewReceiptView newReceiptView = new NewReceiptView();
        return newReceiptView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_receipt, container, false);
        ButterKnife.bind(this, view);
        getActivityComponent().inject(this);
        setupViewPager();
        return view;
    }


    @Override
    protected void setupDialogTitle() {
        if (activity instanceof HomeActivity) {
            ((HomeActivity) activity).setToolbar(0, "");
        }
    }

    private void setupViewPager() {
        NewReceiptAdapter newReceiptAdapter = new NewReceiptAdapter(getActivity(), getChildFragmentManager());
        if (generateQRCodeView == null) {
            generateQRCodeView = GenerateQRCodeView.newInstance();
        }
        if (confirmByPhoneView == null) {
            confirmByPhoneView = ConfirmByPhoneView.newInstance();
        }

        newReceiptAdapter.addFragment(generateQRCodeView, getResources().getString(R.string.qr_code));
        newReceiptAdapter.addFragment(confirmByPhoneView, getResources().getString(R.string.request_pay));
        viewPager.setAdapter(newReceiptAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(positionPage);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                positionPage = position;

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

}
