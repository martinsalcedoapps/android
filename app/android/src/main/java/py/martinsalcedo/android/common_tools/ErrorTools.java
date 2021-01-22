package py.martinsalcedo.android.common_tools;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

import py.martinsalcedo.android.R;

public class ErrorTools {

    public static void displayErrorCode(Context context, String title, String errorCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(getErrorDescription(context, errorCode));
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
        if (!errorResId.equals(0)) {
            try {
                errorDescription = context.getString(errorResId);
            } catch (Exception e) {
                errorDescription = "Internal Error";
            }
        }
        return errorDescription;
    }
}
