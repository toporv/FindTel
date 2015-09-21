package com.example.vili.findtel;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FindTelActivity extends Activity{
    SharedPreferences pref;
    String simStocat="Null";
    String simSerialNumber="Null";
    String telAmic="Nesetat";
    EditText nrAmic;
    Button confirma;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref= getSharedPreferences("myPref", Context.MODE_PRIVATE);
        final SharedPreferences.Editor edit=pref.edit();
        //edit.putString("telAmic", "Nesetat");
        //edit.putString("simStocat", "No sim");
        //edit.commit();
        TelephonyManager tm=(TelephonyManager)getSystemService(TELEPHONY_SERVICE);
        simSerialNumber = tm.getSimSerialNumber();
        simStocat=pref.getString("simStocat", "No sim");
        if(simStocat.equals("No sim")) {
            edit.putString("simStocat",simSerialNumber);
            edit.commit();
        }
        telAmic= pref.getString("telAmic", "Nesetat");
        if(telAmic.equals("Nesetat")){
            setContentView(R.layout.activity_find_tel);
            nrAmic=(EditText)findViewById(R.id.amicText);
            confirma=(Button)findViewById(R.id.okButton);
            nrAmic.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    telAmic=nrAmic.getText().toString();
                    Log.d("Nr", telAmic);
                    if(telAmic.length()==10){
                        confirma.setEnabled(true);
                        confirma.requestFocus();
                    }
                }
            });

            confirma.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    edit.putString("telAmic", telAmic);
                    edit.commit();
                    FindTelActivity.this.finish();
                }
            });
        }
        else{
            this.finish();
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_find_tel, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
