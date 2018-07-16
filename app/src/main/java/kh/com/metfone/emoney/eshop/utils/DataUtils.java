package kh.com.metfone.emoney.eshop.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;

import kh.com.metfone.emoney.eshop.R;
import kh.com.metfone.emoney.eshop.consts.Const;
import kh.com.metfone.emoney.eshop.data.models.CommonConfigInfo;
import kh.com.metfone.emoney.eshop.data.models.UserInformation;

import java.text.DecimalFormat;

/**
 * Created by DCV on 3/1/2018.
 */

public class DataUtils {
    public static final String PHONE_NUMBER_OF_CAMBODIA_EXPRESSION = "((^0|855|)((60)|(90)|(6[6-8])))\\d{6}$|((^0|855|)((31)|(63)||(71)|(88)|(97))\\d{7}$)";
//    public static final String USERNAME_PATTERN = "^[a-zA-Z0-9_.]{6,50}$";
    public static final String USERNAME_PATTERN = "\\b[a-zA-Z][a-zA-Z0-9\\._\\-]{3,}\\b";
    public static final String FULLNAME_PATTERN = "\\b[a-zA-Z][a-zA-Z0-9\\._\\ \\-]{3,}\\b";
    public static CommonConfigInfo commonConfigInfo;
    public static UserInformation userInformation;
    public static final DecimalFormat formatterDouble = new DecimalFormat("#,###,###,###.00");
    public static final DecimalFormat formatterSmallDouble = new DecimalFormat("0.00");
    public static final DecimalFormat formatterLong = new DecimalFormat("#,###,###,###");

    public static boolean checkIsPhoneNumber(String phoneNumber, String expression) {
        return phoneNumber.matches(expression);
    }

    public static long convertUsdToKhr(double money, String configValue) {
        long moneyKhr = 0;
        if (money > 0) {
            int configValueNumber = Integer.parseInt(configValue);
            moneyKhr = Math.round(money * configValueNumber);
        }
        return moneyKhr;
    }

    public static double convertKhrToUsd(long money, String configValue) {
        double moneyUsd = 0;
        if (money > 0) {
            int configValueNumber = Integer.parseInt(configValue);
            moneyUsd = Math.round(money);
            moneyUsd = moneyUsd / configValueNumber;
        }
        return moneyUsd;
    }

    public static String getCurrencyCode(Context context, String type) {
        String currencyCode;
        if (type.equals(context.getResources().getString(R.string.percent))) {
            currencyCode = Const.VALUE_PERCENT_CURRENCY_CODE;
        } else if (type.equals(context.getResources().getString(R.string.usd))) {
            currencyCode = Const.VALUE_USD_CURRENCY_CODE;
        } else {
            currencyCode = Const.VALUE_KHR_CURRENCY_CODE;
        }
        return currencyCode;
    }

    public static CommonConfigInfo getCommonConfigInfo() {
        return commonConfigInfo;
    }

    public static void setCommonConfigInfo(CommonConfigInfo commonInfo) {
        commonConfigInfo = commonInfo;
    }

    public static UserInformation getUserInformation() {
        return userInformation;
    }

    public static void setUserInformation(UserInformation userInformation) {
        DataUtils.userInformation = userInformation;
    }

    public static String calculatorDiscount(String currencyCode, int discountType, String discount, String discountAmount) {
        String discountString = null;
        if (Const.VALUE_USD_CURRENCY_CODE.equals(currencyCode)) {
            switch (String.valueOf(discountType)) {
                case Const.VALUE_PERCENT_CURRENCY_CODE:
                    discountString = new StringBuilder(discount).append("% ≈ ")
                            .append(DataUtils.getDoubleString(Double.parseDouble(discountAmount)))
                            .append(" USD")
                            .toString();
                    break;
                case Const.VALUE_USD_CURRENCY_CODE:
                    discountString = new StringBuilder(DataUtils.getDoubleString(Double.parseDouble(discountAmount)))
                            .append(" USD")
                            .toString();
                    break;
                case Const.VALUE_KHR_CURRENCY_CODE:
                    discountString = new StringBuilder(formatterLong.format(Double.parseDouble(discount)))
                            .append("KHR ≈ ")
                            .append(DataUtils.getDoubleString(Double.parseDouble(discountAmount)))
                            .append(" USD")
                            .toString();
                    break;
            }
        } else {
            switch (String.valueOf(discountType)) {
                case Const.VALUE_PERCENT_CURRENCY_CODE:
                    discountString = new StringBuilder(discount).append("% = ")
                            .append(formatterLong.format(Double.parseDouble(discountAmount)))
                            .append(" KHR")
                            .toString();
                    break;
                case Const.VALUE_USD_CURRENCY_CODE:
                    discountString = new StringBuilder(DataUtils.getDoubleString(Double.parseDouble(discount))).append("USD = ")
                            .append(formatterLong.format(Double.parseDouble(discountAmount)))
                            .append(" KHR")
                            .toString();
                    break;
                case Const.VALUE_KHR_CURRENCY_CODE:
                    discountString = new StringBuilder(formatterLong.format(Double.parseDouble(discountAmount)))
                            .append(" KHR")
                            .toString();
                    break;
            }

        }
        return discountString;
    }

    public static String calculatorDiscountForPrint(String currencyCode, int discountType, String discount, String discountAmount) {
        String discountString = null;
        if (Const.VALUE_USD_CURRENCY_CODE.equals(currencyCode)) {
            switch (String.valueOf(discountType)) {
                case Const.VALUE_PERCENT_CURRENCY_CODE:
                    discountString = new StringBuilder(discount).append("% = ")
                            .append(DataUtils.getDoubleString(Double.parseDouble(discountAmount)))
                            .append(" USD")
                            .toString();
                    break;
                case Const.VALUE_USD_CURRENCY_CODE:
                    discountString = new StringBuilder(DataUtils.getDoubleString(Double.parseDouble(discountAmount)))
                            .append(" USD")
                            .toString();
                    break;
                case Const.VALUE_KHR_CURRENCY_CODE:
                    discountString = new StringBuilder(formatterLong.format(Double.parseDouble(discount)))
                            .append("KHR = ")
                            .append(DataUtils.getDoubleString(Double.parseDouble(discountAmount)))
                            .append(" USD")
                            .toString();
                    break;
            }
        } else {
            switch (String.valueOf(discountType)) {
                case Const.VALUE_PERCENT_CURRENCY_CODE:
                    discountString = new StringBuilder(discount).append("% = ")
                            .append(formatterLong.format(Double.parseDouble(discountAmount)))
                            .append(" KHR")
                            .toString();
                    break;
                case Const.VALUE_USD_CURRENCY_CODE:
                    discountString = new StringBuilder(DataUtils.getDoubleString(Double.parseDouble(discount))).append("USD = ")
                            .append(formatterLong.format(Double.parseDouble(discountAmount)))
                            .append(" KHR")
                            .toString();
                    break;
                case Const.VALUE_KHR_CURRENCY_CODE:
                    discountString = new StringBuilder(formatterLong.format(Double.parseDouble(discountAmount)))
                            .append(" KHR")
                            .toString();
                    break;
            }

        }
        return discountString;
    }

    public static String getPhoneNumber(String phoneNumber) {
        if(phoneNumber.startsWith("0")) {
            return String.valueOf(Long.parseLong(phoneNumber));
        }
        if(phoneNumber.startsWith("855")) {
            return phoneNumber.substring(3);
        }
        return phoneNumber;
    }
    public final static boolean isValidEmail(String emailText) {
        return (!TextUtils.isEmpty(emailText) && Patterns.EMAIL_ADDRESS.matcher(emailText).matches());
    }

    public static String getDoubleString(double number) {
        String result;
        if(number < 1) {
            result = formatterSmallDouble.format(number);
            if(result.contains(",")){
                return result.replace(",",".");
            }
        } else {
            result = formatterDouble.format(number);
            if(result.charAt(result.length()-3)==',' ){
                result = result.replace(",","tmp");
                result = result.replace(".",",");
                result = result.replace("tmp",".");
            }
        }
        return result;
    }

    public static String getLongString(long number) {
        String result = formatterLong.format(number);
            if(result.contains(".")){
                return result.replace(".",",");
            }
        return result;
    }
}