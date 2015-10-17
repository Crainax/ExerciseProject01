package com.ruffneck.mobilesafer.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.telephony.SmsMessage;

import com.ruffneck.mobilesafer.R;
import com.ruffneck.mobilesafer.service.LocationService;

/**
 * Created by 佛剑分说 on 2015/10/15.
 */
public class SMSReceiver extends BroadcastReceiver {


    private SharedPreferences mPref;

    @Override
    public void onReceive(Context context, Intent intent) {
        Object[] pdus = (Object[]) intent.getExtras().get("pdus");

        mPref = context.getSharedPreferences("config", Context.MODE_PRIVATE);

        for (Object pdu : pdus) {

            SmsMessage sms = SmsMessage.createFromPdu((byte[]) pdu);

            String address = sms.getOriginatingAddress();
            String body = sms.getMessageBody();
            System.out.println(address + ":" + body);

            if (address.equals(mPref.getString("sim_phone", null))) {

                if ("#*alarm*#".equals(body)) {
                    MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.ylzs);
                    mediaPlayer.setVolume(1f, 1f);
                    mediaPlayer.setLooping(true);
                    mediaPlayer.start();
                    abortBroadcast();
                } else if ("#*location*#".equals(body)) {

                    context.startService(new Intent(context, LocationService.class));
                    String location = mPref.getString("location", "geting location.....");
                    System.out.println(location);

                    abortBroadcast();
                }

            }


        }
    }




}
