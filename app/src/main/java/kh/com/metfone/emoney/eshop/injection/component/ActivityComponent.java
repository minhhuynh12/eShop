package kh.com.metfone.emoney.eshop.injection.component;

import kh.com.metfone.emoney.eshop.injection.PerActivity;
import kh.com.metfone.emoney.eshop.injection.module.ActivityModule;
import kh.com.metfone.emoney.eshop.ui.changepassword.ChangePasswordDialog;
import kh.com.metfone.emoney.eshop.ui.choosecategory.ChooseCategoryDialog;
import kh.com.metfone.emoney.eshop.ui.chooseshop.ChooseShopDialog;
import kh.com.metfone.emoney.eshop.ui.confirmphone.CheckStatusUSSDView;
import kh.com.metfone.emoney.eshop.ui.confirmphone.ConfirmByPhoneView;
import kh.com.metfone.emoney.eshop.ui.forgotpassword.ForgotPasswordView;
import kh.com.metfone.emoney.eshop.ui.generateqrcode.CheckStatusQRCodeView;
import kh.com.metfone.emoney.eshop.ui.generateqrcode.GenerateQRCodeView;
import kh.com.metfone.emoney.eshop.ui.home.HomeActivity;
import kh.com.metfone.emoney.eshop.ui.login.LoginView;
import kh.com.metfone.emoney.eshop.ui.newreceipt.NewReceiptView;
import kh.com.metfone.emoney.eshop.ui.newreceipt.ViewReceiptView;
import kh.com.metfone.emoney.eshop.ui.receipts.ReceiptsView;
import kh.com.metfone.emoney.eshop.ui.register.RegisterView;
import kh.com.metfone.emoney.eshop.ui.reports.ReportsView;
import kh.com.metfone.emoney.eshop.ui.setting.ChangeExchangeRateDialog;
import kh.com.metfone.emoney.eshop.ui.setting.ChoosePrinterDeviceDialog;
import kh.com.metfone.emoney.eshop.ui.setting.SettingView;
import kh.com.metfone.emoney.eshop.ui.shopdetail.ShopDetailView;
import kh.com.metfone.emoney.eshop.ui.shoplist.ShopListView;
import kh.com.metfone.emoney.eshop.ui.shopupdate.ChooseAreaDialog;
import kh.com.metfone.emoney.eshop.ui.shopupdate.ShopUpdateView;
import kh.com.metfone.emoney.eshop.ui.staffcreate.StaffCreateNewView;
import kh.com.metfone.emoney.eshop.ui.staffdetail.StaffDetailView;
import kh.com.metfone.emoney.eshop.ui.staffs.StaffListView;
import kh.com.metfone.emoney.eshop.ui.staffupdate.StaffUpdateView;
import kh.com.metfone.emoney.eshop.ui.startapp.StartAppView;


import dagger.Component;

/**
 * Created by DCV on 3/1/2018.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)

public interface ActivityComponent {

    void inject(HomeActivity homeActivity);

    void inject(RegisterView registerView);

    void inject(LoginView loginView);

    void inject(CheckStatusQRCodeView checkStatusQRCodeView);

    void inject(NewReceiptView receiptView);

    void inject(GenerateQRCodeView generateQRCodeView);

    void inject(ConfirmByPhoneView generateQRCodeView);

    void inject(CheckStatusUSSDView checkStatusUSSDView);

    void inject(ReceiptsView receiptsView);

    void inject(ReportsView reportsView);

    void inject(StartAppView startAppView);

    void inject(ForgotPasswordView forgotPasswordView);

    void inject(ViewReceiptView viewReceiptView);

    void inject(ChangePasswordDialog changePasswordDialog);

    void inject(SettingView settingView);

    void inject(ChangeExchangeRateDialog changeExchangeRateDialog);

    void inject(ChoosePrinterDeviceDialog choosePrinterDeviceDialog);

    void inject(ChooseShopDialog chooseShopDialog);

    void inject(StaffListView staffListView);

    void inject(StaffCreateNewView staffCreateNewView);

    void inject(StaffDetailView staffDetailView);

    void inject(StaffUpdateView staffUpdateView);

    void inject(ShopListView shopListView);

    void inject(ShopDetailView shopDetailView);

    void inject(ChooseAreaDialog chooseAreaDialog);

    void inject(ShopUpdateView shopUpdateView);

    void inject(ChooseCategoryDialog chooseCategoryDialog);
}
