package common_tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.View;

import py.martinsalcedo.android.R;

public class UpdateAlertDialog {

    public static Dialog updateConfirmation(Activity activity, final View view) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(activity);

        dialog.setTitle(activity.getString(R.string.Confirmation));
        dialog.setMessage(activity.getString(R.string.ThereIsNewUpdate));
        dialog.setPositiveButton(activity.getString(R.string.UpdateNow), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                GlobalTools tools = new GlobalTools();
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
