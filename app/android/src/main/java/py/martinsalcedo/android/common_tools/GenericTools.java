package py.martinsalcedo.android.common_tools;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class GenericTools {

    //2021-01-21 - Review OK
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        View currentView = activity.getCurrentFocus();
        if (imm == null) {
            return;
        }
        if (currentView == null) {
            return;
        }
        imm.hideSoftInputFromWindow(currentView.getWindowToken(), 0);
    }

    //2021-01-21 - Review OK
    public static void showKeyboard(Activity activity) {
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
}
