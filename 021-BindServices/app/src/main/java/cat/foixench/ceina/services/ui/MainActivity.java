package cat.foixench.ceina.services.ui;

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
    boolean mServiceBounded = false;
    MusicService musicService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout);

        final ImageButton btnPlay = (ImageButton) findViewById(R.id.btnPlay);
        ImageButton btnStop = (ImageButton) findViewById(R.id.btnStop);
        final ImageButton btnPause = (ImageButton) findViewById(R.id.btnPause);

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mServiceBounded) {
                    musicService.playMusic();
                    btnPause.setVisibility(View.VISIBLE);
                    btnPlay.setVisibility(View.INVISIBLE);
                }
            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mServiceBounded) {
                    musicService.pauseMusic();
                    btnPause.setVisibility(View.INVISIBLE);
                    btnPlay.setVisibility(View.VISIBLE);
                }
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mServiceBounded) {
                    musicService.stopMusic();
                    // mostramos los botones
                    btnPause.setVisibility(View.INVISIBLE);
                    btnPlay.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // iniciamos y enlazamos con el servicio
        // enlazamos con el servicio
        Intent intent = new Intent(MainActivity.this, MusicService.class);
        startService (intent);
        bindService(intent, musicServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mServiceBounded) {
            // deshacemos el vinculo con el servicio para evitar tener errores cuando la activity pasa a segundo plano
            unbindService(musicServiceConnection);
            mServiceBounded = false;
        }
    }

    private ServiceConnection musicServiceConnection = new ServiceConnection() {
        // cuando se conecta el servicio, guardamos una instancia del mismo para poder manipularlo.
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            MusicService.MusicServiceBinder musicServiceBinder = (MusicService.MusicServiceBinder) service;
            musicService = musicServiceBinder.getService();
            mServiceBounded = true;
        }

        // desconexión del servicio
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mServiceBounded = false;
        }
    };
}
