package cat.foixench.ceina.pushnotifications.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.messaging.FirebaseMessaging;

import cat.foixench.ceina.pushnotifications.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);

        // suscirpcion a los temas de mensajeria. para una aplicación concreta solo recibiremos notificaciones
        // de los temas a los que estemos suscritos.
        FirebaseMessaging.getInstance().subscribeToTopic("test_mensajeria");


    }
}
