package com.example.vili.findtel;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Vili on 9/6/2015.
 */
public class SpamSMS extends BroadcastReceiver{
    SharedPreferences pref;
    String simStocat="Null";
    String simSerialNumber="Null";
    String telNumber="Null";
    String telAmic="Nesetat";
    double latitudine=0;
    double longitudine=0;
    String dataora;
    SmsManager sms;
    String Msg="Nimic de comunicat";
    boolean gpsEnabled=false;
    boolean networkEnabled=false;
    boolean passiveEnabled=false;
    Location location;
    String provider="No provider";
    @Override
    public void onReceive(Context context, Intent intent) {

        pref= context.getSharedPreferences("myPref", Context.MODE_PRIVATE);
        final SharedPreferences.Editor edit=pref.edit();

        telAmic= pref.getString("telAmic", "Nesetat");


        simStocat=pref.getString("simStocat", "No sim");

        TelephonyManager tm=(TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        simSerialNumber = tm.getSimSerialNumber();
        telNumber=tm.getLine1Number();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd_HH:mm:ss");
        dataora = sdf.format(new Date());

        if(simStocat.equals(simSerialNumber)){
        //Toast.makeText(context, "Egale", Toast.LENGTH_LONG).show();
        Intent myIntent=new Intent(context, SpamSMS.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(context, 12, myIntent,0);
        AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        am.cancel(pendingIntent);
        pendingIntent.cancel();
         }
        else
            {
                    DataEnabled dataEnabled=new DataEnabled();
                    dataEnabled.mobiledataenable(context, true);
                    LocationManager locManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                    gpsEnabled = locManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                    networkEnabled = locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                    passiveEnabled = locManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER);


                    final LocationListener locationListener = new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            latitudine = location.getLatitude();
                            longitudine = location.getLongitude();
                        }

                        @Override
                        public void onStatusChanged(String provider, int status, Bundle extras) {

                        }

                        @Override
                        public void onProviderEnabled(String provider) {

                        }

                        @Override
                        public void onProviderDisabled(String provider) {

                        }
                    };

                if (gpsEnabled) {
                    provider = LocationManager.GPS_PROVIDER;
                    locManager.requestLocationUpdates(provider, 0, 0, locationListener);
                    location = locManager.getLastKnownLocation(provider);
                    //Toast.makeText(context, provider, Toast.LENGTH_LONG).show();
                }

                if (location == null && networkEnabled) {
                        provider = LocationManager.NETWORK_PROVIDER;
                        locManager.requestLocationUpdates(provider, 0, 0, locationListener);
                        location = locManager.getLastKnownLocation(provider);
                        //Toast.makeText(context, provider, Toast.LENGTH_LONG).show();
                }
                if(location == null && passiveEnabled) {
                        provider = LocationManager.PASSIVE_PROVIDER;
                        locManager.requestLocationUpdates(provider, 0, 0, locationListener);
                        location = locManager.getLastKnownLocation(provider);
                        //Toast.makeText(context, provider, Toast.LENGTH_LONG).show();
                }

                    if(location!=null) {
                        latitudine = location.getLatitude();
                        longitudine = location.getLongitude();

                    }
                    Msg = dataora + " Nr:" + telNumber + " SIM:" + simSerialNumber +" " + provider + " lat:" + String.valueOf(latitudine) + " long:" + String.valueOf(longitudine);
                    //Toast.makeText(context, Msg, Toast.LENGTH_LONG).show();
                    sms = SmsManager.getDefault();
                    sms.sendTextMessage(telAmic, null, Msg, null, null);
                }
    }

}




