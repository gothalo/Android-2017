package cat.foixench.ceina.services.services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import cat.foixench.ceina.services.R;

/**
 * Created by llorenc on 07/11/2017.
 */

public class MusicService extends Service {

    MediaPlayer mediaPlayer;

    // vamos a crear un servicio sin Bining, por lo que devolveremos null
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // creación del servicio
    @Override
    public void onCreate() {
        super.onCreate();

    }

    /**
     * Metodo ejecutado cuando se llama al servicio.
     * @param intent puede ser nulo en reinicios en los que no se haya devuelto START_REDELIVER_INTENT
     * @param flags 0 o una combinacion de
     *              START_FLAG_REDELIVERY -> Se recibe un intent en un servicio START_REDELIVER_INTENT
     *              START_FLAG_RETRY -> se trata de un reintento debido a que la llamada inicial no retorno de onStartCommand
     *
     * @param startId
     * @return definie el comportamiento del servicio si este es matado por el sistema.
     * START_STICKY si se desea que el servicio vuelva a reinicializarse tan pronto como sea posible cuando sea detenido por el sistema
     * START_NOT_STICKY solo se reinicia el servicio si se vuelve a realizar una llamada a startService
     * START_REDELIVER_INTENT se reinicia el servicio tan pronto como sea posible usando el mismo intent que en su ultimo inicio
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.explosive_ear_candy_new_life);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                // cuando completamos la música finalizamos el servicio
                stopSelf();
            }
        });
        mediaPlayer.start();

        return START_STICKY; // si se interrumpe el servicio se volverá a ejecutar

    }

    // finaliza el servicio. liberar recursos
    @Override
    public void onDestroy() {
        mediaPlayer.stop();
        super.onDestroy();

    }
}
