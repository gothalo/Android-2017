package cat.foixench.ceina.broadcastreceivers.activities;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


import java.util.Calendar;

import cat.foixench.ceina.broadcastreceivers.R;

public class TimerActivity extends AppCompatActivity {

    private static BroadcastReceiver tickReceiver;
    AnimatorSet set;

    TextView tvHour, tvSeparator, tvMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        tvHour = (TextView) findViewById(R.id.tvHour);
        tvSeparator = (TextView) findViewById(R.id.tvSeparator);
        tvMinute = (TextView) findViewById(R.id.tvMinute);

        tvHour.setText(getHour());
        tvMinute.setText(getMinute());

        // animacion en el separador de horas
        set = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.ticker_tick);
        set.setTarget(tvSeparator);
        set.start();


    }

    @Override
    protected void onResume() {
        super.onResume();
        // registramos un broadcast receiver
        tickReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().compareTo(Intent.ACTION_TIME_TICK) == 0) {
                    tvHour.setText(getHour());
                    tvMinute.setText(getMinute());
                }
            }
        };
        registerReceiver(tickReceiver, new IntentFilter(Intent.ACTION_TIME_TICK));
        if (!set.isRunning()) {
            set.start();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        // eliminamos el registro del broadcast receiver.
        if(tickReceiver!=null) {
            unregisterReceiver(tickReceiver);
        }
        // animacion en el separador de horas
        set.cancel();
    }

    private String getMinute () {
        String minute = "00" + String.valueOf(Calendar.getInstance().get(Calendar.MINUTE));
        return minute.substring(minute.length() - 2);
    }

    private String getHour () {
        String hour = "00" + String.valueOf(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
        return hour.substring(hour.length() - 2);
    }

}
