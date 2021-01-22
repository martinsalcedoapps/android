package py.martinsalcedo.android.common_tools;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

public class PlayStoreTools {

    void goToPlayStore(View view) {
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
}
