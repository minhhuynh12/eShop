package kh.com.metfone.emoney.eshop.ui.newreceipt;

import android.bluetooth.BluetoothDevice;

import kh.com.metfone.emoney.eshop.data.domain.GetLocalCommonConfigUseCase;
import kh.com.metfone.emoney.eshop.data.domain.GetUserInforUseCase;
import kh.com.metfone.emoney.eshop.data.models.UserInformation;
import kh.com.metfone.emoney.eshop.ui.base.BasePresenter;
import kh.com.metfone.emoney.eshop.utils.SharePreferenceHelper;

import javax.inject.Inject;

import io.reactivex.subscribers.DisposableSubscriber;

/**
 * Created by DCV on 3/15/2018.
 */

public class ViewReceiptPresenter extends BasePresenter<ViewReceiptMvpView> {

    private final GetLocalCommonConfigUseCase getLocalCommonConfigUseCase;
    private final SharePreferenceHelper sharePreferenceHelper;
    private final GetUserInforUseCase getUserInforUseCase;


    @Inject
    ViewReceiptPresenter(GetLocalCommonConfigUseCase getLocalCommonConfigUseCase,
                         GetUserInforUseCase getUserInforUseCase,
                         SharePreferenceHelper sharePreferenceHelper) {
        this.getLocalCommonConfigUseCase = getLocalCommonConfigUseCase;
        this.sharePreferenceHelper = sharePreferenceHelper;
        this.getUserInforUseCase = getUserInforUseCase;
    }

    @Override
    public void attachView(ViewReceiptMvpView mvpView) {
        super.attachView(mvpView);
        getPrinterAddress();
        getUserInfo();
        getUserLogin();
    }

    private void getUserLogin() {
        if (getMvpView() != null) {
            getMvpView().getUserLogin(sharePreferenceHelper.getUserName());
        }
    }

    private void getUserInfo() {
        getUserInforUseCase.execute(new DisposableSubscriber<UserInformation>() {
            @Override
            public void onNext(UserInformation userInformation) {
                if (getMvpView() != null) {
                    getMvpView().getUserInfoSuccess(userInformation);
                }
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        }, null);
    }

    private void getPrinterAddress() {
        if (getMvpView() != null) {
            getMvpView().getPrinterAddressSuccess(sharePreferenceHelper.getPrinterAddress());
        }
    }

    @Override
    public void detachView() {
        if (getLocalCommonConfigUseCase != null) {
            getLocalCommonConfigUseCase.dispose();
        }
        super.detachView();
    }

    public void savePrinterAddress(BluetoothDevice bluetoothDevice) {
        sharePreferenceHelper.putPrinterName(bluetoothDevice.getName());
        sharePreferenceHelper.putPrinterAddress(bluetoothDevice.getAddress());
    }

}
