package sanghirun.yongyut.myofficer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by ubc15 on 1/14/2017.
 */

public class MyAlert {

    //Explicit
    private Context context;

    public MyAlert(Context context) {
        this.context = context;
    }

    public void errorDialog(String strTitle, String strMessage) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setIcon(R.drawable.doremon48);
        builder.setTitle(strTitle);
        builder.setMessage(strMessage);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();

            }
        });
        builder.show();



    }   //errorDialog

}   //Main Class

