package cat.foixench.ceina.persistencia.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;


import cat.foixench.ceina.persistencia.R;
import cat.foixench.ceina.persistencia.utils.SharedPrefsInterface;

/**
 * Created by llorenc on 12/10/2017.
 */

public class LaunchActivity extends Activity implements SharedPrefsInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent;

        // recuperamos las preferencias del sistema
        SharedPreferences prefs = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
        String prefUser = prefs.getString(PARAM_USER_NAME, DEFAULT_VALUE_EMPTY);

        Log.d(APP_TAG, PARAM_USER_NAME + " [" + prefUser + "] ");

        // si no existe el parametro en el fichero de preferencias, se habrá recuperado DEFAULT_VALUE_EMPTY
        if (prefUser.equals(DEFAULT_VALUE_EMPTY)) {
            intent = new Intent(this, PreferencesActivity.class);
            intent.putExtra(INTENT_EXTRA_ACTIVITY_LAUNCH, INTENT_EXTRA_ACTIVITY_LAUNCH_VALUE_LAUNCH);
        } else {
            intent = new Intent (this, MainActivity.class);
        }
        startActivity(intent);
    }
}
