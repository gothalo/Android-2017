package cat.foixench.ceina.services.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;

import cat.foixench.ceina.services.R;
import cat.foixench.ceina.services.ui.MainActivity;
import cat.foixench.ceina.services.utils.Utils;

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

    // Binder que permite la comunciación del service con las activityes
    public class MusicServiceBinder extends Binder {
        // a través de este método podremos manipular los elementos del servicio
        public MusicService getService () {
            return MusicService.this;
        }
    }

    public void playMusic () {
        if (mediaPlayer != null) {
            mediaPlayer.start();

            createNotification();

        }
    }

    public void pauseMusic () {
        if (mediaPlayer != null) {
            mediaPlayer.pause();

            // cuando nos pulsen el botón pausar vamos a esconder la notificación
            NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
            nm.cancel(Utils.NOTIFICATION_ID);
        }
    }

    public void stopMusic () {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            // dejamos el media player preparado para una próxima ejecución
            mediaPlayer.prepareAsync();
        }
    }

    private void createNotification () {

        // recuperación de los metadatos del mp3
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        Uri mediaPath = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.explosive_ear_candy_new_life);
        mmr.setDataSource(this, mediaPath);

        // configuramos la notificación
        String notificationText  = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST) + " - " + mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);

        NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(R.drawable.ic_music_playing)
                .setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(),R.mipmap.ic_launcher))
                .setContentTitle(getResources().getString(R.string.notification_title))
                .setContentText(notificationText)
                .setColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));

        // no queremos que se elimine la notificación si el usuario interactua con ella.
        notificationBuilder.setAutoCancel(false);


        /***************************************************************************************/
        /** queremos que si se pulsa sobre la notficación, esta abra la activity MainActivity **/

        Intent intent = new Intent (getApplicationContext(), MainActivity.class);

        // creamos la pila de la llamada
        TaskStackBuilder stackBuilder =  TaskStackBuilder.create(getApplicationContext());
        // añadir actividad al stack Builder
        stackBuilder.addParentStack(MainActivity.class);

        // indicar el intent para la notificacion
        stackBuilder.addNextIntent(intent);

        // obtener un pending intent de la pila. Este intent no se ejecutará hasta que el usuario interactue con la notificación
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(Utils.REQUEST_CODE, PendingIntent.FLAG_UPDATE_CURRENT);

        // asociamos el PendingIntent a la notificacion
        notificationBuilder.setContentIntent(resultPendingIntent);

        /***************************************************************************************/


        /*******************************************************/
        /** añadir una acción personalizada a la notificación **/
        Intent intentPause = new Intent (getApplicationContext(), MainActivity.class);
        intentPause.setAction (Utils.ACTION_PAUSE);
        PendingIntent pausePendingIntent = PendingIntent.getActivity (getApplicationContext(), Utils.REQUEST_CODE, intentPause, PendingIntent.FLAG_UPDATE_CURRENT);

        notificationBuilder.addAction(R.drawable.ic_pause, getResources().getString(R.string.notification_pause_text), pausePendingIntent);

        /*******************************************************/


        // lanzar la notifiación
        nm.notify(Utils.NOTIFICATION_ID, notificationBuilder.build());
    }
}
