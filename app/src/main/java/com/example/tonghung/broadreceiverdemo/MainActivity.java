package com.example.tonghung.broadreceiverdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PersistableBundle;
import android.service.restrictions.RestrictionsReceiver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView tvSMS;
    private BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvSMS = (TextView) findViewById(R.id.textViewSMS);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(context, "SMS has received!", Toast.LENGTH_SHORT).show();

                Bundle bundle = intent.getExtras();
                Object[] arrObj = (Object[]) bundle.get("pdus");

                String sms = "";
                for(int i=0; i<arrObj.length; i++){
                    SmsMessage msg = SmsMessage.createFromPdu((byte[]) arrObj[i]);
                    String body = msg.getMessageBody();
                    String add = msg.getDisplayOriginatingAddress();
                    sms+="Address: " +add+ "\n" + "Body: " +body+ "\n";
                }

                tvSMS.setText(sms);
            }
        };

        registerReceiver(receiver, intentFilter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
