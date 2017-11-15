package cat.foixench.ceina.localbroadcast.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import cat.foixench.ceina.localbroadcast.R;


/**
 * Created by llorenc on 15/11/2017.
 */

public class RunningService extends Service {

    private static final String TAG = "RunningService";
    public static final String ACTION_SERVICE_FINISHED = "cat.foixench.ceina.localbroadcast.ACTION_SERVICE_FINISHED";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent,int flags, int startId) {

        Toast.makeText(getApplicationContext(), R.string.service_start, Toast.LENGTH_SHORT).show();

        Thread task = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    Log.d(TAG, "Interrupted", e);
                }

                // lanzamos el broadcast de finalización
                Intent endIntent = new Intent(ACTION_SERVICE_FINISHED);
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(endIntent);

                stopSelf();
            }
        };

        task.start();

        return START_NOT_STICKY;

    }
}
