package cat.foixench.ceina.services.services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;

import cat.foixench.ceina.services.R;

/**
 * Created by llorenc on 07/11/2017.
 */

public class MusicService extends Service {

    private MediaPlayer mediaPlayer;

    private final IBinder musicServiceBinder = new MusicServiceBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        // creamos el media player
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.explosive_ear_candy_new_life);
    }

    /**
     * Ese método se ejecuta cuando se inicializa el servicio mediante bindService
     * @param intent Intent a ejecutar
     * @return
     */
    @Override
    public IBinder onBind(Intent intent) {
        return musicServiceBinder;
    }

    @Override
    public void onDestroy() {
        mediaPlayer.stop();
        mediaPlayer.release();
        super.onDestroy();
    }

    // métodos de control del servicio
    public void playMusic () {
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    public void pauseMusic () {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    public void stopMusic () {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            // dejamos el media player preparado para una próxima ejecución
            mediaPlayer.prepareAsync();
        }
    }

    // Binder que permite la comunciación del service con las activities.
    public class MusicServiceBinder extends Binder {
        // Permite recuperar una instancia de este servicios
        public MusicService getService () {
            return MusicService.this;
        }
    }

}
