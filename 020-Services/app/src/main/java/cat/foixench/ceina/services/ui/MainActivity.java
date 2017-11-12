package cat.foixench.ceina.services.ui;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import cat.foixench.ceina.services.R;
import cat.foixench.ceina.services.services.MusicService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout);

        ImageButton btnPlay = (ImageButton) findViewById(R.id.btnPlay);
        ImageButton btnStop = (ImageButton) findViewById(R.id.btnStop);

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // enlazamos el servicio
                Intent intent = new Intent(MainActivity.this, MusicService.class);
                startService (intent);
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService(new Intent (MainActivity.this, MusicService.class));
            }
        });
    }

}
