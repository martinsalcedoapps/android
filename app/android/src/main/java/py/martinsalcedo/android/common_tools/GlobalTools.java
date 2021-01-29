package py.martinsalcedo.android.common_tools;

import android.content.Context;
import android.content.Intent;
import android.widget.RelativeLayout;

import java.text.DecimalFormat;
import java.util.Locale;

import py.martinsalcedo.android.web_services.CheckForUpdatesConsult;

/**
 * Created by msalcedo on 15/10/2017.
 * GlobalTools: Revision 24/07/2018
 */

public class GlobalTools {

    public static void checkForUpdates(Context context, RelativeLayout rlMainActivityLayout, String application_id, String thisVersion, String host, Integer port) {
        CheckForUpdatesConsult consult = new CheckForUpdatesConsult(context, rlMainActivityLayout, host, port, thisVersion,"CheckForUpdates");
        consult.setMethod("GET");
        consult.addProperty("TOKEN", SharedPreferencesTools.getStringPreference(context, LibraryConstants.FIREBASE_TOKEN));
        consult.addProperty("APPLICATION", application_id);
        consult.addProperty("VERSION_CODE", thisVersion);
        consult.addProperty("DEV_COUNTRY", Locale.getDefault().getCountry());
        consult.execute();
    }

    public static String formatValue(Context context, Double value, Integer precision) {
        StringBuilder amountPattern = new StringBuilder("###,##0");
        if (precision > 0) {
            amountPattern.append(".");
        }
        for (int i = 0; i < precision; i++) {
            amountPattern.append("0");
        }
        if (value == null) {
            value = 0d;
        }
        DecimalFormat amountFormat = new DecimalFormat(amountPattern.toString());
        String totalAmountStr = amountFormat.format(value);
        return totalAmountStr;
    }

    public static void shareApplication(Context context) {
        final String appPackageName = context.getPackageName(); // getPackageName() from Context or Activity object
        String stringShare = "https://play.google.com/store/apps/details?id=" + appPackageName;
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, stringShare);
        sendIntent.setType("text/plain");
        context.startActivity(sendIntent);
    }



}
