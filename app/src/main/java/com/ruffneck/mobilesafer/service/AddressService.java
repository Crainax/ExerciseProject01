package com.ruffneck.mobilesafer.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.ruffneck.mobilesafer.db.dao.AddressDAO;

/**
 * Created by 佛剑分说 on 2015/10/30.
 */
public class AddressService extends Service {

    private MyPhoneStateListener mylistener;
    private TelephonyManager manager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        manager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        mylistener = new MyPhoneStateListener();
        manager.listen(mylistener, PhoneStateListener.LISTEN_CALL_STATE);
    }

    class MyPhoneStateListener extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            if (state == TelephonyManager.CALL_STATE_RINGING) {
                String address = AddressDAO.getAddress(incomingNumber);
                Toast.makeText(getBaseContext(),address,Toast.LENGTH_SHORT).show();
            }
            super.onCallStateChanged(state, incomingNumber);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        manager.listen(mylistener, PhoneStateListener.LISTEN_NONE);
    }
}
