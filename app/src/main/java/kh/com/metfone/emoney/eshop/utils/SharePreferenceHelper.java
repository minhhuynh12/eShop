package kh.com.metfone.emoney.eshop.utils;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class   SharePreferenceHelper {

    private static final String USER_LANGUAGE = "UserLanguage";
    private static final String PREF_ACCESS_TOKEN = "access_token";
    private static final String PREF_USERNAME = "username";
    private static final String PREF_PRINTER_NAME = "printer_name";
    private static final String PREF_PRINTER_ADDRESS = "printer_address";
    private static final String PREF_FIREBASE_TOKEN = "firebase_token";
    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    @Inject
    public SharePreferenceHelper(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
        this.editor = sharedPreferences.edit();
    }


    public void putLanguage(String language) {
        editor.putString(USER_LANGUAGE, language);
        editor.apply();
    }

    public String getLanguage() {
        return sharedPreferences.getString(USER_LANGUAGE, "");
    }

    public void putAccessToken(String accessToken) {
        editor.putString(PREF_ACCESS_TOKEN, accessToken);
        editor.apply();
    }

    public String getAccessToken() {
        return sharedPreferences.getString(PREF_ACCESS_TOKEN, "");
    }

    public void putUserName(String username) {
        editor.putString(PREF_USERNAME, username);
        editor.apply();
    }

    public String getUserName() {
        return sharedPreferences.getString(PREF_USERNAME, "");
    }

    public void putPrinterName(String printerName) {
        editor.putString(PREF_PRINTER_NAME, printerName);
        editor.apply();
    }

    public String getPrinterName() {
        return sharedPreferences.getString(PREF_PRINTER_NAME, "");
    }

    public void putPrinterAddress(String printerAddress) {
        editor.putString(PREF_PRINTER_ADDRESS, printerAddress);
        editor.apply();
    }

    public String getPrinterAddress() {
        return sharedPreferences.getString(PREF_PRINTER_ADDRESS, "");
    }

    public void putFireBaseToken(String firebaseToken) {
        editor.putString(PREF_FIREBASE_TOKEN, firebaseToken);
        editor.apply();
    }

    public String getFireBaseToken() {
        return sharedPreferences.getString(PREF_FIREBASE_TOKEN, "");
    }
}
