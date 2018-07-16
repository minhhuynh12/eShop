package kh.com.metfone.emoney.eshop.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import kh.com.metfone.emoney.eshop.MyApplication;
import kh.com.metfone.emoney.eshop.injection.component.ActivityComponent;
import kh.com.metfone.emoney.eshop.injection.component.DaggerActivityComponent;
import kh.com.metfone.emoney.eshop.injection.module.ActivityModule;

/**
 * Created by DCV on 3/2/2018.
 */
public class BaseFragment extends Fragment {
    protected FragmentActivity activity;
    ActivityComponent mActivityComponent;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = getActivity();
    }

    public ActivityComponent getActivityComponent() {
        if (mActivityComponent == null) {
            mActivityComponent = DaggerActivityComponent.builder()
                    .applicationComponent(MyApplication.get(getActivity()).getComponent())
                    .activityModule(new ActivityModule(getActivity()))
                    .build();
        }
        return mActivityComponent;
    }

    public void replaceFragment(Fragment fragment, int containerId, boolean addToBackStack) {
        if (addToBackStack) {
            getChildFragmentManager().beginTransaction().
                    replace(containerId, fragment).
                    addToBackStack(fragment.getClass().getSimpleName()).
                    commit();
        } else {
            getChildFragmentManager().beginTransaction().
                    replace(containerId, fragment).
                    commit();
        }
    }

    public void closeSoftKeyboard() {
        View focusedView = activity.getCurrentFocus();
        if (focusedView != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
        }
    }

}
