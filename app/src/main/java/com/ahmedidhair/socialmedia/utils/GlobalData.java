package com.ahmedidhair.socialmedia.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.drawerlayout.widget.DrawerLayout;

import com.ahmedidhair.socialmedia.R;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by Dev. Ahmed Idhair on 9/4/2017.
 */

public class GlobalData {

    static ProgressDialog progressDialog;
    static AlertDialog alertDialog;


    public static void progressDialog(Context c, String msg, boolean status) {
        // to show dialog insert status = true to dismiss doialog status = false
        if (status) {
            progressDialog = new ProgressDialog(c);
            progressDialog.setMessage(msg);
            progressDialog.setCancelable(false);
            if (progressDialog != null)
                if (!progressDialog.isShowing())
                    progressDialog.show();
        } else {
            if (progressDialog != null)
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
        }
    }


    public static void Toast(Activity c, String msg) {
        if (c != null) {
            Toast.makeText(c, msg, Toast.LENGTH_SHORT).show();
        }
    }

    public static void Snackbar(View v, String msg) {
        if (v != null) {
            Snackbar.make(v, msg, Snackbar.LENGTH_SHORT).show();
        }
    }




    public static void closeKeybord(Activity c) {
        View view = c.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) c.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

    }


    public static String arabicToDecimal(String number) {
        char[] chars = new char[number.length()];
        for (int i = 0; i < number.length(); i++) {
            char ch = number.charAt(i);
            if (ch >= 0x0660 && ch <= 0x0669)
                ch -= 0x0660 - '0';
            else if (ch >= 0x06f0 && ch <= 0x06F9)
                ch -= 0x06f0 - '0';
            chars[i] = ch;
        }
        return new String(chars);
    }

    public static String splitDate(String dateText) {

        String CurrentString = dateText;
        String[] separated = CurrentString.split(" ");
        String date = separated[0];
        return date;
    }

    public static String timeConversion(int totalSeconds) {

        final int MINUTES_IN_AN_HOUR = 60;
        final int SECONDS_IN_A_MINUTE = 60;

        int seconds = totalSeconds % SECONDS_IN_A_MINUTE;
        int totalMinutes = totalSeconds / SECONDS_IN_A_MINUTE;
        int minutes = totalMinutes % MINUTES_IN_AN_HOUR;
        int hours = totalMinutes / MINUTES_IN_AN_HOUR;

        String timer = ("" + String.format("%02d", hours) + " :  "
                + String.format("%02d", minutes) + " : "
                + String.format("%02d", seconds));

        return timer;
    }


    public static String convertMeterToKm(int lastValue) {


        String km = String.valueOf(lastValue / 1000);

        return km + " km";
    }

    public static String GetLongDateString(long time) {

        String dateString = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date(time));

//        DateFormat parser = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);
//        try {
//            return parser.format(d);
//        } catch (Exception e) {
//
//        }
        return dateString;
    }

    public static String GetLongTimeString(Date time) {
        return new SimpleDateFormat("hh:mm aa", Locale.US).format(time);
    }

    public static String GetLongTimeStr(long time) {
        return new SimpleDateFormat("hh:mm aa", Locale.US).format(new Date(time));
    }

    public static String GetLongDateTimeString(Date time) {
        return new SimpleDateFormat("yyyy/MM/dd hh:mm aa", Locale.US).format(time);
    }

    private static String arabicToenglish(String number) {
        char[] chars = new char[number.length()];
        for (int i = 0; i < number.length(); i++) {
            char ch = number.charAt(i);
            if (ch >= 0x0660 && ch <= 0x0669)
                ch -= 0x0660 - '0';
            else if (ch >= 0x06f0 && ch <= 0x06F9)
                ch -= 0x06f0 - '0';
            chars[i] = ch;
        }
        return new String(chars);
    }

    public static String GetDateString(Object d) {

        DateFormat parser = new SimpleDateFormat("dd MMM yyyy");
        Date date = GetDate(d);
        try {
            return parser.format(date);
        } catch (Exception e) {

        }
        return "";
    }

    public static Date GetDate(Object d) {
        DateFormat parser = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        try {
            date = parser.parse(String.valueOf(d));
        } catch (Exception e) {

        }
        return date;
    }


    public static long GetLongDate(String date) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd",Locale.US);
        long milliseconds = 0;
        try {
            Date d = f.parse(date);
            milliseconds = d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return milliseconds;
    }

    public static String getAddress(Activity activity, double latitude, double longitude) {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(activity, Locale.getDefault());
        String address = "";
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            System.out.println("Log address " + address);

        } catch (IOException e) {
            e.printStackTrace();
        }

//

        return address;

    }


    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static String removeZeroFromPhoneNumber(String num) {
//        String number = num.replaceFirst("^0+(?!$)", "");
        String number = num.replaceFirst("0", "");

        System.out.println("Log number " + number);
        return number;
    }

    public static String removeZeroFromPhoneNumber(long time) {
        Date date = new Date(time);
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String dateFormatted = formatter.format(date);
        return dateFormatted;
    }

    public static boolean isValidPhone(CharSequence phone) {
        if (TextUtils.isEmpty(phone)) {
            return false;
        } else {
            return android.util.Patterns.PHONE.matcher(phone).matches();
        }
    }


    public static boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (email.isEmpty() || !email.matches(emailPattern)) {
            return false;
        } else {
            return true;
        }
    }

    //
//
//    public static String getLang(Context activity) {
//        SharePManger shareManger = new SharePManger(activity);
//        String lang = shareManger.getDataString("lang");
//        if (lang == null) {
//            shareManger.SetData("lang", "en");
//            return "ar";
//        } else {
//            return lang;
//
//        }
//    }

    public static String convertDate(String strCurrentDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.US);
        Date newDate = null;
        try {
            newDate = format.parse(strCurrentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String date = format.format(newDate);
        return date;
    }

    public static String formatTimeFromDate(String strCurrentDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.US);
        Date newDate = null;
        try {
            newDate = format.parse(strCurrentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        format = new SimpleDateFormat("hh:mm:ss", Locale.US);
        String date = format.format(newDate);
        return date;
    }

    public static String formatTime(String strCurrentDate) {
        SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
        Date newDate = null;
        try {
            newDate = format.parse(strCurrentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        format = new SimpleDateFormat("hh:mm");
        String date = format.format(newDate);
        return date;
    }


    public static boolean validCellPhone(String number) {
        return android.util.Patterns.PHONE.matcher(number).matches();
    }


    public static void callNumber(Activity activity, String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        activity.startActivity(intent);
    }
//
//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    public static void setStatusBarGradiant(Activity activity) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = activity.getWindow();
//            Drawable background = activity.getResources().getDrawable(R.drawable.gradient_theme);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(activity.getResources().getColor(android.R.color.transparent));
//            window.setNavigationBarColor(activity.getResources().getColor(android.R.color.transparent));
//            window.setBackgroundDrawable(background);
//        }
//    }



    public static void openWebUrl(Activity activity, String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        activity.startActivity(i);
    }

    public static void openLogin(Activity activity) {
        //Intent intent = new Intent(activity, LoginActivity.class);
       // activity.startActivity(intent);
    }

    public static boolean isValid(String url) {
        /* Try creating a valid URL */
        try {
            new URL(url).toURI();
            return true;
        }

        // If there was an Exception
        // while creating URL object
        catch (Exception e) {
            return false;
        }
    }


    public static void shareTextUrl(Activity activity, String url, String subject) {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        // Add data to the intent, the receiving app will decide
        // what to do with it.
        share.putExtra(Intent.EXTRA_SUBJECT, subject);
        share.putExtra(Intent.EXTRA_TEXT, url);

        activity.startActivity(Intent.createChooser(share, activity.getString(R.string.app_name)));
    }
}
