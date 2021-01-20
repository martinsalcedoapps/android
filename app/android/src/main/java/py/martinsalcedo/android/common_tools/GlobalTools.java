package py.martinsalcedo.android.common_tools;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AlertDialog;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.components.BuildConfig;

import java.util.Locale;

import py.martinsalcedo.android.R;
import py.martinsalcedo.android.web_services.CheckForUpdatesConsult;


/**
 * Created by msalcedo on 15/10/2017.
 * GlobalTools: Revision 24/07/2018
 */

public class GlobalTools {

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        View currentView = activity.getCurrentFocus();
        //Log.i("Keyboard", "OFF");
        if (imm == null) {
            return;
        }
        if (currentView == null) {
            return;
        }
        imm.hideSoftInputFromWindow(currentView.getWindowToken(), 0);
    }

    public static void showKeyboard(Activity activity) {
        //Log.i("Keyboard", "ON");
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        View currentView = activity.getCurrentFocus();
        if (imm == null) {

            return;
        }
        if (currentView == null) {

            return;
        }
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, -1);

    }

    public static void sendAnalytics(Context context, String action, String detail) {
        FirebaseAnalytics mFirebaseAnalytics;
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, detail);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, detail);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, action);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }

    //###############	SHARED PREFERENCES ##########
    public static void setIntPreference(Context context, String key, Integer value) {
        final String appPackageName = context.getPackageName();
        SharedPreferences sharedPref = context.getSharedPreferences(appPackageName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static Integer getIntPreference(Context context, String key) {
        final String appPackageName = context.getPackageName();
        SharedPreferences sharedPref = context.getSharedPreferences(appPackageName, Context.MODE_PRIVATE);
        return sharedPref.getInt(key, -1);

    }

    public static void setStringPreference(Context context, String key, String value) {
        final String appPackageName = context.getPackageName();
        SharedPreferences sharedPref = context.getSharedPreferences(appPackageName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getStringPreference(Context context, String key) {
        final String appPackageName = context.getPackageName();
        SharedPreferences sharedPref = context.getSharedPreferences(appPackageName, Context.MODE_PRIVATE);
        return sharedPref.getString(key, "");
    }

    //#################################################

    public static void displayErrorCode(Context context, String title, String errorCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(GlobalTools.getErrorDescription(context, errorCode));
        builder.setPositiveButton(context.getString(R.string.OK), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == DialogInterface.BUTTON_POSITIVE) {
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();
    }

    public static void displayErrorDescription(Context context, String title, String errorDescription) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(errorDescription);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == DialogInterface.BUTTON_POSITIVE) {
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();
    }

    private static String getErrorDescription(Context context, String errorCode) {
        String errorDescription = "Internal Error";
        String stringErrorCode = String.format("Error%s", errorCode);
        Integer errorResId = context.getResources().getIdentifier(stringErrorCode, "string", context.getPackageName());
        if (!errorResId.equals(0) && errorResId != null) {
            try {
                errorDescription = context.getString(errorResId);
            } catch (Exception e) {
                errorDescription = "Internal Error";
            }
        }
        return errorDescription;
    }

    public void goToPlayStore(View view) {
        Log.i("View", view.toString());
        Context context = view.getContext().getApplicationContext();
        final String appPackageName = context.getPackageName(); // getPackageName() from Context or Activity object
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (android.content.ActivityNotFoundException anfe) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception errString) {
            errString.printStackTrace();
        }
    }

    public static void checkForUpdates(Context context, RelativeLayout rlMainActivityLayout) {
        CheckForUpdatesConsult consult = new CheckForUpdatesConsult(context, rlMainActivityLayout, "CheckForUpdates");
        consult.setMethod("GET");
        consult.addProperty("TOKEN", GlobalTools.getStringPreference(context, Constants.FIREBASE_TOKEN));
        consult.addProperty("APPLICATION", BuildConfig.APPLICATION_ID);
        consult.addProperty("VERSION_CODE", String.valueOf(BuildConfig.VERSION_CODE));
        consult.addProperty("DEV_COUNTRY", Locale.getDefault().getCountry());
        consult.execute();
    }

}
