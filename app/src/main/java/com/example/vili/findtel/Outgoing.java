package com.example.vili.findtel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Vili on 9/13/2015.
 */
public class Outgoing extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //Toast.makeText(context, "Outgoing", Toast.LENGTH_LONG).show();
        UnSMS unHot=new UnSMS();
        unHot.sms(context, intent);
    }
}
