package cat.foixench.ceina.pushnotifications.services;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;

import cat.foixench.ceina.pushnotifications.R;
import cat.foixench.ceina.pushnotifications.activities.MainActivity;
import cat.foixench.ceina.pushnotifications.activities.SecondActivity;

/**
 * Created by llorenc on 12/11/2017.
 */

public class FirebaseMessangingService extends com.google.firebase.messaging.FirebaseMessagingService {

    private static final String TAG = "PUSH_NOTIFICATION";

    public FirebaseMessangingService() {
        
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        if (remoteMessage.getData().size() > 0) {
            // procesa los datos extra de la notificación
        }

        if (remoteMessage.getNotification() != null) {
            String title = remoteMessage.getNotification().getTitle();
            String message = remoteMessage.getNotification().getBody();
            String clickAction = remoteMessage.getNotification().getClickAction();


            Log.d(TAG, "Title: " + title);
            Log.d(TAG, "Message: " + message);
            Log.d(TAG, "clickAction: " + clickAction);

            sendNotification(title, message, clickAction);
        }

    }


    @Override
    public void onDeletedMessages() {

    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param message mensaje en la notificacion
     * @param title titulo de la notificacion
     */
    private void sendNotification(String title, String message, String clickAction) {
        Intent intent;

        if (clickAction.equals("OPENACTIVITY")) {
            intent = new Intent(this, SecondActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        } else {
            intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_push_notification)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
