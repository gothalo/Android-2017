package cat.foixench.ceina.registrodellamadas;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.widget.TextView;

import cat.foixench.ceina.registrodellamadas.utils.AppUtils;

/**
 * Created by llorenc on 22/10/2017.
 */

public class CallDetailActivity extends AppCompatActivity {
    public static final String EXTRA_ID = "ID";

    private TextView tvCallNumber, tvCallDate, tvCallDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
        tvCallNumber = (TextView) findViewById(R.id.tvCallNumber);
        tvCallDate = (TextView) findViewById(R.id.tvCallDate);
        tvCallDuration = (TextView) findViewById(R.id.tvCallDuration);

        // recuperamos el id de la llamada
        Intent intent = getIntent();

        long id = intent.getLongExtra(EXTRA_ID, 0);

        if (id > 0) {
            loadAndShowData (id);
        }
    }

    private void loadAndShowData(long id) {

        ContentResolver contentResolver = this.getContentResolver();
        Uri llamadasUri = ContentUris.withAppendedId(CallLog.Calls.CONTENT_URI, id);

        long date = 0, duration = 0;
        String number = "";

        String [] projection = new String [] {CallLog.Calls._ID, CallLog.Calls.NUMBER, CallLog.Calls.DURATION, CallLog.Calls.DATE};

        // verificar permisos de llamada
        int permissionCheck = ContextCompat.checkSelfPermission (this, Manifest.permission.READ_CALL_LOG);

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Cursor cursor = contentResolver.query(llamadasUri, // URI del Content provider
                                            projection,        // Columnas a devolver
                                            null,              // Condición a la query
                                            null,              // Argumentos variables de la condición
                                            null);             // Orden de los resultados

            if (cursor != null) {
                cursor.moveToFirst();
                date = cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE));
                duration = cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DURATION));
                number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));

                cursor.close();
            }


            // mostramos los datos leidos en pantalla
            tvCallNumber.setText(number);
            tvCallDate.setText(DateUtils.getRelativeTimeSpanString(this, date));
            tvCallDuration.setText(AppUtils.secondsToHoutMinSecond(duration));

        }


    }
}
