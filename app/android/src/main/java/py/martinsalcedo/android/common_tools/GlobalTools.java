package py.martinsalcedo.android.common_tools;

import android.content.Context;
import android.widget.RelativeLayout;

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
        consult.addProperty("TOKEN", SharedPreferences.getStringPreference(context, LibraryConstants.FIREBASE_TOKEN));
        consult.addProperty("APPLICATION", application_id);
        consult.addProperty("VERSION_CODE", thisVersion);
        consult.addProperty("DEV_COUNTRY", Locale.getDefault().getCountry());
        consult.execute();
    }

}
