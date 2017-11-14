package cat.foixench.ceina.timer;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;


public class MainActivity extends AppCompatActivity {

    TimerService timer;
    boolean isBinded;
    TextView tvEllapsedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnStart = (Button) findViewById(R.id.btnStart);
        Button btnStop = (Button) findViewById(R.id.btnStop);
        tvEllapsedTime = (TextView) findViewById(R.id.tvEllapsedTime);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBinded) {
                    timer.start();
                }
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBinded) {
                    long time = timer.stop();
                    String ellapsed = DateUtils.formatElapsedTime(time / 1000);
                    tvEllapsedTime.setText(ellapsed);
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, TimerService.class);
        startService(intent);
        bindService(intent,timerServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(timerServiceConnection);
    }

    ServiceConnection timerServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            TimerService.TimerServiceBinder binder = (TimerService.TimerServiceBinder) iBinder;
            timer = binder.getService();
            isBinded = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isBinded = false;
        }
    };
}
