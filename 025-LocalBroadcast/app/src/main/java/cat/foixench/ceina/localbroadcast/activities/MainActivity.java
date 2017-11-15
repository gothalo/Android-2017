package cat.foixench.ceina.localbroadcast.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import cat.foixench.ceina.localbroadcast.R;
import cat.foixench.ceina.localbroadcast.services.RunningService;

/**
 * Created by llorenc on 15/11/2017.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout);

        // crear el servicio
        Intent intent = new Intent(this, RunningService.class);
        startService(intent);

    }

    @Override
    protected void onResume() {
        // Escuchamos los broadcast locales
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessengerReceiver, new IntentFilter(RunningService.ACTION_SERVICE_FINISHED));
        super.onResume();
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessengerReceiver);
        super.onPause();
    }

    public BroadcastReceiver mMessengerReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, R.string.service_end, Toast.LENGTH_LONG).show();
        }
    };

}
