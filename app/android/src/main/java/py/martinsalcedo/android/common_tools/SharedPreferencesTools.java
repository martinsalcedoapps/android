package py.martinsalcedo.android.common_tools;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SharedPreferencesTools {

    //###############	SHARED PREFERENCES ##########
    public static void setIntPreference(Context context, String key, Integer value) {
        final String appPackageName = context.getPackageName();
        android.content.SharedPreferences sharedPref = context.getSharedPreferences(appPackageName, Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static Integer getIntPreference(Context context, String key, Integer def_decimals) {
        final String appPackageName = context.getPackageName();
        android.content.SharedPreferences sharedPref = context.getSharedPreferences(appPackageName, Context.MODE_PRIVATE);
        return sharedPref.getInt(key, def_decimals);

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

    public static void setStringSetPreference(Context context, String key, ArrayList elements) {
        final String appPackageName = context.getPackageName();
        android.content.SharedPreferences sharedPref = context.getSharedPreferences(appPackageName, Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor editor = sharedPref.edit();
        Set<String> stringSet = new HashSet<>();
//        editor.putInt(key, value);
        for (int idx = 0; idx < elements.size(); idx++) {
            stringSet.add(String.format("%s|%s", idx, elements.get(idx)));
        }
        editor.putStringSet(key, stringSet);
        editor.apply();
    }

    public static Set<String> getStringSetPreference(Context context, String key) {
        final String appPackageName = context.getPackageName();
        android.content.SharedPreferences sharedPref = context.getSharedPreferences(appPackageName, Context.MODE_PRIVATE);
        return sharedPref.getStringSet(key, new HashSet<String>(0));
    }

    public static void removePreference(Context context, String key) {
        final String appPackageName = context.getPackageName();
        android.content.SharedPreferences sharedPref = context.getSharedPreferences(appPackageName, Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove(key);
        editor.apply();
    }

}
