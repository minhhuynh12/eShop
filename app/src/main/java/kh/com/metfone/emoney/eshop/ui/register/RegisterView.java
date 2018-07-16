package kh.com.metfone.emoney.eshop.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import kh.com.metfone.emoney.eshop.R;
import kh.com.metfone.emoney.eshop.consts.Const;
import kh.com.metfone.emoney.eshop.data.event.ChooseCategoryEvent;
import kh.com.metfone.emoney.eshop.data.event.ChooseCategoryParentEvent;
import kh.com.metfone.emoney.eshop.ui.base.BaseDialogActivity;
import kh.com.metfone.emoney.eshop.ui.choosecategory.ChooseCategoryDialog;
import kh.com.metfone.emoney.eshop.utils.AppUtils;
import kh.com.metfone.emoney.eshop.utils.SharePreferenceHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterView extends BaseDialogActivity implements RegisterMvpView {
    @BindView(R.id.edt_account)
    EditText edtAccount;
    @BindView(R.id.edt_code)
    EditText edtCode;
    @BindView(R.id.edt_password)
    EditText edtPassword;
    @BindView(R.id.edt_category)
    EditText edtCategory;
    @BindView(R.id.edt_shop_name)
    EditText edtShopName;
    @BindView(R.id.edt_full_address)
    EditText edtFullAddress;
    @BindView(R.id.txt_get_code)
    TextView txt_get_code;
    @Inject
    RegisterPresenter registerPresenter;
    ChooseCategoryDialog chooseCategoryDialog;
    @Inject
    SharePreferenceHelper sharePreferenceHelper;

    int shop_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getActivityComponent().inject(this);
        AppUtils.setLanguage(this, sharePreferenceHelper.getLanguage());
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        registerPresenter.attachView(this);
    }

    @OnClick({R.id.txt_close, R.id.btn_register, R.id.img_drop_arrow, R.id.txt_get_code})
    public void onClickEvent(View view) {
        switch (view.getId()) {
            case R.id.txt_close:
                finish();
                break;
            case R.id.btn_register:
                checkEmptyField();
                break;
            case R.id.img_drop_arrow:
                chooseCategoryDialog = new ChooseCategoryDialog(RegisterView.this, getActivityComponent());
                chooseCategoryDialog.show();
                break;
            case R.id.txt_get_code:
                checkValidateFields();
                break;
        }
    }

    private void checkPhoneNumer() {
        if (AppUtils.isConnectivityAvailable(RegisterView.this)) {
            registerPresenter.getCodeForRegister(edtAccount.getText().toString());
        } else {
            notifyError(R.string.MSG_CM_NO_NETWORK_FOUND);
        }
    }

    private void checkValidateFields() {
        if (edtAccount.getText().toString().trim().isEmpty()) {
            notifyError(R.string.ERR_RQ_001_EMONEY_ACCOUNT);
            return;
        } else {
            checkPhoneNumer();
        }
    }

    @Subscribe
    public void chooseCategoryEvent(ChooseCategoryEvent chooseCateEvent) {
        chooseCategoryDialog.dismiss();
        edtCategory.setText(chooseCateEvent.getShopInfor().getShopTypeName());
        shop_id = chooseCateEvent.getShopInfor().getShopTypeId();
//        getReceipt();
    }

    @Subscribe
    public void chooseParentCategoryEvent(ChooseCategoryParentEvent chooseCateEvent) {
        chooseCategoryDialog.dismiss();
        edtCategory.setText(chooseCateEvent.getShopInfor().getShopTypeName());
        shop_id = chooseCateEvent.getShopInfor().getShopTypeId();
//        getReceipt();
    }

    private void checkEmptyField() {
        if (edtAccount.getText().toString().trim().isEmpty()) {
            notifyError(R.string.ERR_RQ_001_EMONEY_ACCOUNT);
            return;
        }
        if (edtCode.getText().toString().trim().isEmpty()) {
            notifyError(R.string.ERR_RQ_002_PRIVATE_CODE);
            return;
        }
        if (edtPassword.getText().toString().isEmpty()) {
            notifyError(R.string.ERR_RQ_003_NEW_PASSWORD);
            return;
        }
        if (edtCategory.getText().toString().trim().isEmpty()) {
            notifyError(R.string.ERR_RQ_004_CATEGORY);
            return;
        }
        if (edtShopName.getText().toString().trim().isEmpty()) {
            notifyError(R.string.ERR_RQ_005_SHOP_NAME);
            return;
        }
        if (edtFullAddress.getText().toString().trim().isEmpty()) {
            notifyError(R.string.ERR_RQ_006_SHOP_ADDRESS);
            return;
        }
        if (AppUtils.isConnectivityAvailable(RegisterView.this)) {
            registerPresenter.register(edtAccount.getText().toString().trim(), edtCode.getText().toString().trim(), edtPassword.getText().toString(),
                    String.valueOf(shop_id), edtShopName.getText().toString().trim(), edtFullAddress.getText().toString().trim());
        } else {
            notifyError(R.string.MSG_CM_NO_NETWORK_FOUND);
        }
    }

    @Override
    protected void setupDialogTitle() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void notifiEmptyFields() {

    }

    @Override
    public void onSuccessRegister(String message) {
        Intent intent = getIntent();
        intent.putExtra(Const.KEY_MESSAGE, message);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
