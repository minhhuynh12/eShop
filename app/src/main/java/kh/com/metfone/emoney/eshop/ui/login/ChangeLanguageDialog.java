package kh.com.metfone.emoney.eshop.ui.login;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import kh.com.metfone.emoney.eshop.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by DCV on 3/12/2018.
 */

public class ChangeLanguageDialog extends Dialog {

    private Context context;
    private PublishSubject<String> changeLanguagePublish;
    private String language;
    @BindView(R.id.rad_cambodia_language)
    AppCompatRadioButton cambodiaRadioButton;
    @BindView(R.id.rad_english_language)
    AppCompatRadioButton englishRadioButton;
    @BindView(R.id.rg_choose_language)
    RadioGroup radioGroup;

    public ChangeLanguageDialog(Context context, int themeResId, String language) {
        super(context, themeResId);
        this.context = context;
        this.language = language;
        changeLanguagePublish = PublishSubject.create();
    }

    public PublishSubject<String> getChangeLanguagePublish() {
        return changeLanguagePublish;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_change_language);
        setCancelable(true);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        DisplayMetrics dm = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
        getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        getWindow().setGravity(Gravity.BOTTOM);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        if(language.equals(context.getResources().getString(R.string.en_language))) {
            englishRadioButton.setChecked(true);
        } else {
            cambodiaRadioButton.setChecked(true);
        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.rad_cambodia_language) {
                    changeLanguagePublish.onNext(context.getResources().getString(R.string.km_language));
                } else {
                    changeLanguagePublish.onNext(context.getResources().getString(R.string.en_language));
                }
                dismiss();
            }
        });
    }
}
