package com.sudarabisheck.freshcatch.android;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextThemeWrapper;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.sudarabisheck.freshcatch.ConfirmInterface;
import com.sudarabisheck.freshcatch.FreshCatchGame;
import com.sudarabisheck.freshcatch.RequestHandler;

public class AndroidLauncher extends AndroidApplication implements RequestHandler  {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new FreshCatchGame(this), config);
	}

    @Override
    public void confirm(final ConfirmInterface confirmInterface) {
        runOnUiThread(new Runnable(){
            @Override
            public void run() {
                AlertDialog.Builder bld;

                if (android.os.Build.VERSION.SDK_INT <= 10) {
                    //With default theme looks perfect:
                    bld = new AlertDialog.Builder(AndroidLauncher.this);
                } else {
                    //With Holo theme appears the double Dialog:
                    bld = new AlertDialog.Builder(new ContextThemeWrapper(AndroidLauncher.this, android.R.style.Theme_Holo_Dialog_MinWidth));
                }
                //new AlertDialog.Builder(AndroidLauncher.this)
                     bld.setIcon(R.drawable.ic_launcher)
                        .setTitle("Alert")
                        .setMessage("Are you sure you want to quit the game?")
                        .setOnCancelListener(new DialogInterface.OnCancelListener() {



                            @Override
                            public void onCancel(DialogInterface dialog) {
                                confirmInterface.no();
                            }
                        })
                        .setPositiveButton("Quit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                confirmInterface.yes();
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("Return", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                confirmInterface.no();
                                dialog.cancel();
                            }
                        })
                        .create().show();
            }
        });
    }
}
