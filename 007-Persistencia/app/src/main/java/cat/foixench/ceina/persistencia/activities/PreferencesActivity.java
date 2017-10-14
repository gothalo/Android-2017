package cat.foixench.ceina.persistencia.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import cat.foixench.ceina.persistencia.R;
import cat.foixench.ceina.persistencia.utils.AppUtils;
import cat.foixench.ceina.persistencia.utils.SharedPrefsInterface;



public class PreferencesActivity extends AppCompatActivity implements SharedPrefsInterface {
    EditText txtNombre;
    CheckBox chkGuardarSD;
    String activityCaller;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView (R.layout.activity_preferences);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtNombre = (EditText) findViewById(R.id.txtPrefNombre);
        chkGuardarSD = (CheckBox) findViewById(R.id.chkPrefSD);

        Intent caller = getIntent();
        activityCaller =  caller.hasExtra(INTENT_EXTRA_ACTIVITY_LAUNCH) ? caller.getStringExtra(INTENT_EXTRA_ACTIVITY_LAUNCH) : "";

    }

    @Override
    protected void onResume() {
        super.onResume();
        readPreferences();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.preferences_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.mniSaveSettings :
                savePreferences();
                if (activityCaller.equals(INTENT_EXTRA_ACTIVITY_LAUNCH_VALUE_LAUNCH)) {
                    // Venimos del inicio. debemos abrir la activity main antes de cerrar esta
                    Intent i = new Intent(this, MainActivity.class);
                    startActivity(i);
                }
                finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void savePreferences() {
        SharedPreferences prefs = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
        // necesitem un editor per canviar els valors
        SharedPreferences.Editor editor = prefs.edit();
        String userName = txtNombre.getText().toString().trim();

        Log.d(APP_TAG, "saving user name [" + userName + "]");

        // guardamos la preferencia
        editor.putString (PARAM_USER_NAME, userName);

        // antes de guardar en la sd verificamos que tengamos permisos
        if (AppUtils.hasPermisionToWriteSD(this)) {
            editor.putBoolean(PARAM_USE_SD, chkGuardarSD.isChecked());
        } else {
            editor.putBoolean(PARAM_USE_SD, false);
            Toast.makeText(this, R.string.err_no_permision, Toast.LENGTH_SHORT).show();
        }

        // se salva el fichero de preferencias.
        editor.commit();
    }

    private void readPreferences () {
        // recuperamos las preferencias del sistema
        String prefUser = AppUtils.getStringPreference(this, PARAM_USER_NAME);
        // la mostramos en pantalla
        txtNombre.setText(prefUser);

        chkGuardarSD.setChecked(AppUtils.getBooleanPreference(this, PARAM_USE_SD));


    }
}
