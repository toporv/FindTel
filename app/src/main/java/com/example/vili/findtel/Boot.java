package com.example.vili.findtel;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Vili on 8/24/2015.
 */
public class Boot extends BroadcastReceiver {
    Intent myIntent;
    @Override
    public void onReceive(Context context, Intent intent) {
            //Toast.makeText(context, "boot", Toast.LENGTH_LONG).show();
            Intent myIntent = new Intent(context, SpamSMS.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 12, myIntent, 0);
            AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            am.setInexactRepeating(AlarmManager.ELAPSED_REALTIME, 5 * 1000, 1800 * 1000, pendingIntent);
    }
}
