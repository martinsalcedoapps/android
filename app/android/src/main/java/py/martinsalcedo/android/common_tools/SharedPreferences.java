package py.martinsalcedo.android.common_tools;

import android.content.Context;

public class SharedPreferences {

    //###############	SHARED PREFERENCES ##########
    public static void setIntPreference(Context context, String key, Integer value) {
        final String appPackageName = context.getPackageName();
        android.content.SharedPreferences sharedPref = context.getSharedPreferences(appPackageName, Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static Integer getIntPreference(Context context, String key) {
        final String appPackageName = context.getPackageName();
        android.content.SharedPreferences sharedPref = context.getSharedPreferences(appPackageName, Context.MODE_PRIVATE);
        return sharedPref.getInt(key, -1);

    }

    public static void setStringPreference(Context context, String key, String value) {
        final String appPackageName = context.getPackageName();
        android.content.SharedPreferences sharedPref = context.getSharedPreferences(appPackageName, Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getStringPreference(Context context, String key) {
        final String appPackageName = context.getPackageName();
        android.content.SharedPreferences sharedPref = context.getSharedPreferences(appPackageName, Context.MODE_PRIVATE);
        return sharedPref.getString(key, "");
    }
}
