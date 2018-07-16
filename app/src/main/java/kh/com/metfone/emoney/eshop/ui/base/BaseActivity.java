package kh.com.metfone.emoney.eshop.ui.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import kh.com.metfone.emoney.eshop.MyApplication;
import kh.com.metfone.emoney.eshop.R;
import kh.com.metfone.emoney.eshop.injection.component.ActivityComponent;
import kh.com.metfone.emoney.eshop.injection.component.DaggerActivityComponent;
import kh.com.metfone.emoney.eshop.injection.module.ActivityModule;
import kh.com.metfone.emoney.eshop.ui.confirmphone.CheckStatusUSSDView;
import kh.com.metfone.emoney.eshop.ui.generateqrcode.CheckStatusQRCodeView;
import kh.com.metfone.emoney.eshop.ui.newreceipt.NewReceiptView;
import kh.com.metfone.emoney.eshop.ui.newreceipt.ViewReceiptView;
import kh.com.metfone.emoney.eshop.ui.setting.SettingView;
import kh.com.metfone.emoney.eshop.ui.shopdetail.ShopDetailView;
import kh.com.metfone.emoney.eshop.ui.shoplist.ShopListView;
import kh.com.metfone.emoney.eshop.ui.staffdetail.StaffDetailView;
import kh.com.metfone.emoney.eshop.ui.staffs.StaffListView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by DCV on 3/1/2018.
 */
public class BaseActivity extends AppCompatActivity {
    public static final int IN_RIGHT_OUT_LEFT = 1;
    public static final int IN_LEFT_OUT_RIGHT = -1;
    public static final int WITHOUT_ANIMATION = 0;
    ActivityComponent mActivityComponent;
    protected Fragment fragmentSave;
    protected Fragment newReceiptFragment;
    protected Fragment fragmentSetting;
    protected Fragment fragmentStaff;
    protected List<Fragment> settingListFragment = new ArrayList<>();
    protected FragmentManager settingFragmentManager;
    protected List<Fragment> staffListFragment = new ArrayList<>();
    protected FragmentManager staffFragmentManager;

    public ActivityComponent getActivityComponent() {
        if (mActivityComponent == null) {
            mActivityComponent = DaggerActivityComponent.builder()
                    .applicationComponent(MyApplication.get(this).getComponent())
                    .activityModule(new ActivityModule(this))
                    .build();
        }
        return mActivityComponent;
    }

    public void replaceFragment(Fragment fragment, int containerId) {
        replaceFragment(fragment, containerId, 0, true);
    }

    public void replaceFragment(Fragment fragment, int containerId, boolean addToBackStack) {
        replaceFragment(fragment, containerId, 0, addToBackStack);
    }

    public void replaceFragment(Fragment fragment, int containerId, int customAnimations, boolean addToBackStack) {
        if (fragment != null) {
            String backStateName = fragment.getClass().getName();
            try {
                boolean fragmentPopped = getSupportFragmentManager().popBackStackImmediate(backStateName, 0);

                if (!fragmentPopped) {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    if (customAnimations == IN_RIGHT_OUT_LEFT) {
                        transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
                    } else if (customAnimations == IN_LEFT_OUT_RIGHT) {
                        transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
                    }
                    transaction.replace(containerId, fragment, fragment.getClass().getSimpleName());
                    if (addToBackStack) {
                        transaction.addToBackStack(fragment.getClass().getSimpleName()).commit();
                    } else {
                        transaction.commit();
                    }
                    if (fragmentSave != null) {
                        fragmentSave = null;
                    }
                }
                if (fragment instanceof NewReceiptView ||
                        fragment instanceof CheckStatusQRCodeView ||
                        fragment instanceof ViewReceiptView ||
                        fragment instanceof CheckStatusUSSDView) {
                    newReceiptFragment = fragment;
                }
                if (fragment instanceof SettingView) {
                    fragmentSetting = fragment;
                }

                if (fragment instanceof StaffListView) {
                    fragmentStaff = fragment;
                }
            } catch (IllegalStateException e) {
                fragmentSave = fragment;
            }
        }
    }

    protected void createShopBackStackList() {
        if (settingFragmentManager.getBackStackEntryCount() > 0) {
            Fragment fragmentInStackFirst = settingFragmentManager.findFragmentByTag(settingFragmentManager.getBackStackEntryAt(0).getName());
            if (fragmentInStackFirst instanceof ShopListView ||
                    fragmentInStackFirst instanceof ShopDetailView) {
                settingListFragment.clear();
                settingListFragment.add(fragmentSetting);
                for (int i = 0; i < settingFragmentManager.getBackStackEntryCount(); i++) {
                    String tag = settingFragmentManager.getBackStackEntryAt(i).getName();
                    Fragment fragmentInStack = settingFragmentManager.findFragmentByTag(tag);
                    settingListFragment.add(fragmentInStack);
                    FragmentTransaction trans = settingFragmentManager.beginTransaction();
                    trans.remove(fragmentInStack);
                    trans.commit();
                    settingFragmentManager.popBackStack();
                }

            }
        } else {
            settingListFragment.clear();
        }
    }

    protected void createStaffBackStackList() {
        if (staffFragmentManager.getBackStackEntryCount() > 0) {
            Fragment fragmentInStackFirst = staffFragmentManager.findFragmentByTag(staffFragmentManager.getBackStackEntryAt(0).getName());
            if (fragmentInStackFirst instanceof StaffDetailView) {
                staffListFragment.clear();
                staffListFragment.add(fragmentStaff);
                for (int i = 0; i < staffFragmentManager.getBackStackEntryCount(); i++) {
                    String tag = staffFragmentManager.getBackStackEntryAt(i).getName();
                    Fragment fragmentInStack = staffFragmentManager.findFragmentByTag(tag);
                    staffListFragment.add(fragmentInStack);
                    FragmentTransaction trans = staffFragmentManager.beginTransaction();
                    trans.remove(fragmentInStack);
                    trans.commit();
                    staffFragmentManager.popBackStack();
                }
            }
        } else {
            staffListFragment.clear();
        }
    }
}
