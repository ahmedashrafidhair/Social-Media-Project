package com.ahmedidhair.socialmedia.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ahmedidhair.socialmedia.R;

import java.util.Objects;



/**
 * Created by Dev. Ahmed Idhair on 8/29/2017.
 */

public class BActivity extends AppCompatActivity {
    public Toolbar toolbar;
   public TextView aTitle;
   public boolean isMainActivity = false;
    String lang;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setStatusBarGradiant(getActivity());
//        if (!UserAuth.getInstance().isSelLang()) {
//            LocaleUtils.setLocale(getActivity(), "ar");
//        }
//        lang = LocaleUtils.getLanguage(getActivity());
//        if (lang.equals("ar")) {
//            Constants.LANGUAGE = "ar";
////            Constants.Currency = "ريال";
//        } else {
//            Constants.LANGUAGE = "en";
////            Constants.Currency = "SAR";
//        }
//        Locale locale = new Locale(lang);
//        Locale.setDefault(locale);
//        Configuration config = new Configuration();
//        config.locale = locale;
//        getBaseContext().getResources().updateConfiguration(config,
//                getBaseContext().getResources().getDisplayMetrics());

    }

    @Override
    public void setTitle(CharSequence title) {
        toolbar = findViewById(R.id.toolbar);
//        menu = toolbar.findViewById(R.id.menu);
        aTitle = toolbar.findViewById(R.id.title);
//        searchImg = toolbar.findViewById(R.id.searchImg);
//        countryImg = toolbar.findViewById(R.id.countryImg);
////        flagImg = toolbar.findViewById(R.id.flagImg);
//        filterImg = toolbar.findViewById(R.id.filterImg);
//        checkImg = toolbar.findViewById(R.id.checkImg);
//        editImg = toolbar.findViewById(R.id.editImg);
//        addImg = toolbar.findViewById(R.id.addImg);
//        deleteImg = toolbar.findViewById(R.id.deleteImg);
        if (!isMainActivity) {
            aTitle.setText(title);
//            menu.setVisibility(View.VISIBLE);
//            if (getCurrentLanguage().getLanguage().equals("ar")) {
//            menu.setImageResource(R.drawable.ic_back_left);
//            } else {
//                menu.setImageResource(R.drawable.ic_left_arrow);
//            }
            aTitle.setVisibility(View.VISIBLE);
//            addImg.setVisibility(View.GONE);
//            editImg.setVisibility(View.GONE);
//            deleteImg.setVisibility(View.GONE);
//            menu.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    finish();
//                }
//            });

        } else {

//            menu.setVisibility(View.VISIBLE);
//            menu.setImageResource(R.drawable.ic_menu);

            aTitle.setVisibility(View.VISIBLE);

            aTitle.setText(title);
//            menu.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    finish();
//                }
//            });


        }

        setSupportActionBar(toolbar);
        if (!isMainActivity) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
//            toolbar.setNavigationIcon(R.drawable.ic_back);

            toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
//            toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);
//            toolbar.setNavigationIcon(R.drawable.ic_menu);
//            toolbar.setNavigationIcon(android.R.drawable.ic_menu_close_clear_cancel);
        }

        super.setTitle(title);
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (!UserAuth.getInstance().isSelLang()) {
//            LocaleUtils.setLocale(getActivity(), "ar");
//        }
////        GlobalData.setStatusBarGradiant(getActivity());
//        lang = LocaleUtils.getLanguage(getActivity());
//        Locale locale = new Locale(lang);
//        Locale.setDefault(locale);
//        Configuration config = new Configuration();
//        config.locale = locale;
//        getBaseContext().getResources().updateConfiguration(config,
//                getBaseContext().getResources().getDisplayMetrics());

    }


//    @Override
//    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
//    }


    protected Activity getActivity() {
        return this;
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusBarGradiant(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = activity.getWindow();
//            Drawable background = activity.getResources().getDrawable(R.drawable.shape_status_bar);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(activity.getResources().getColor(android.R.color.transparent));
//            window.setNavigationBarColor(activity.getResources().getColor(android.R.color.transparent));
//            window.setBackgroundDrawable(background);
        }
    }

    public void startNewActivity(Class<?> newActivity, Intent extras, boolean clearStack) {
        Intent intent = new Intent(this, newActivity);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (clearStack) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
        if (extras != null) {
            intent.putExtras(extras);
        }
        hideKeyboard(this);
        startActivity(intent);
    }

    public static void hideKeyboard(Activity activity) {
        try {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
            }
        } catch (Exception e) {
        }
    }

    public void replaceFragment(Fragment fragment, int container) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void initializeToolbar(String title) {
        Objects.requireNonNull(getSupportActionBar()).setTitle(title);
        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


}
