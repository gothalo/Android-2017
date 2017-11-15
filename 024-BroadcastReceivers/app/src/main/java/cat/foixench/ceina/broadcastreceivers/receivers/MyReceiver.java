package cat.foixench.ceina.broadcastreceivers.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

import cat.foixench.ceina.broadcastreceivers.activities.MainActivity;

/**
 * Reciver que captura los eventos de generación
 */
public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(MainActivity.APP_TAG, "Incoming call");

        Bundle extras = intent.getExtras();
        if (extras != null) {
            String state = extras.getString(TelephonyManager.EXTRA_STATE);

            if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                String phoneNumber = extras.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
                Log.d(MainActivity.APP_TAG, phoneNumber);
            }
            Log.d(MainActivity.APP_TAG, state);
        }

    }
}
