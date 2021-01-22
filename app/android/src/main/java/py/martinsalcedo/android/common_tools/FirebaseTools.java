package py.martinsalcedo.android.common_tools;

import android.content.Context;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

public class FirebaseTools {

    public static void sendAnalytics(Context context, String action, String detail) {
        FirebaseAnalytics mFirebaseAnalytics;
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, detail);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, detail);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, action);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }
}
