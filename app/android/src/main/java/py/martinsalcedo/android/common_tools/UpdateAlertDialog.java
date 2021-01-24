package py.martinsalcedo.android.common_tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.View;

import py.martinsalcedo.android.R;

public class UpdateAlertDialog {

    public static Dialog updateConfirmation(Activity activity, final View view, String old_version, String new_version) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(activity);

        dialog.setTitle(activity.getString(R.string.Confirmation));
        dialog.setMessage(String.format("%s/%s - %s", old_version, new_version, activity.getString(R.string.ThereIsNewUpdate)));
        dialog.setPositiveButton(activity.getString(R.string.UpdateNow), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                PlayStoreTools tools = new PlayStoreTools();
                tools.goToPlayStore(view);
            }
        });
        dialog.setNegativeButton(activity.getString(R.string.Later), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        return dialog.create();

    }

}
