package com.example.vili.findtel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Vili on 9/12/2015.
 */
public class UnSMS {
    SmsManager sms;
    public void sms(Context context, Intent intent){
        SharedPreferences pref= context.getSharedPreferences("myPref", Context.MODE_PRIVATE);
        String telAmic= pref.getString("telAmic", "Nesetat");
        String simStocat=pref.getString("simStocat", "No sim");
        TelephonyManager tm=(TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        String simSerialNumber = tm.getSimSerialNumber();
        if(!simStocat.equals(simSerialNumber)){
        String peCineSuna = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd_HH:mm:ss");
        String dataora = sdf.format(new Date());
        String Msg=dataora + ":Hotul suna la: " + peCineSuna;
        //Toast.makeText(context, Msg, Toast.LENGTH_LONG).show()
        sms = SmsManager.getDefault();
        sms.sendTextMessage(telAmic, null, Msg, null, null);

        }
    }
}
