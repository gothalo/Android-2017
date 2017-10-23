package cat.foixench.ceina.registrodellamadas;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.CallLog.Calls;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import cat.foixench.ceina.registrodellamadas.adapters.CallLogAdapter;

public class MainActivity extends AppCompatActivity {
    private final static int REQUEST_CALL_LOG_PERMISION = 1;
    private ListView lvCalls;
    private CallLogAdapter adapter;
    private Cursor cursor;
    private Button btnAll, btnIncomming, btnOutgoing, btnMissed, btnRejected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new CallLogAdapter(this);
        lvCalls = (ListView) findViewById(R.id.lstLlamadas);

        lvCalls.setAdapter(adapter);

        lvCalls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent (MainActivity.this, CallDetailActivity.class);
                intent.putExtra(CallDetailActivity.EXTRA_ID, id);
                startActivity(intent);

            }
        });


        // nos aseguramos que tenemos los permisos necesarios para usar el registro de llamada.
        // verificar si hay permisos
        if (ContextCompat.checkSelfPermission (this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,  new String [] {Manifest.permission.READ_CALL_LOG}, REQUEST_CALL_LOG_PERMISION);
        }

        btnAll = (Button) findViewById(R.id.btnAll);
        btnIncomming = (Button) findViewById(R.id.btnIncommig);
        btnOutgoing = (Button) findViewById(R.id.btnOutgoing);
        btnMissed = (Button) findViewById(R.id.btnMissed);
        btnRejected =  (Button) findViewById(R.id.btnRejected);

        // eventos de los botones .filtran el listado de llamadas
        btnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // cambiamos el cursor
                MainActivity.this.swapCursor (0);
            }
        });

        btnIncomming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // cambiamos el cursor
                MainActivity.this.swapCursor (Calls.INCOMING_TYPE);
            }
        });

        btnOutgoing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // cambiamos el cursor
                MainActivity.this.swapCursor (Calls.OUTGOING_TYPE);
            }
        });

        btnMissed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // cambiamos el cursor
                MainActivity.this.swapCursor (Calls.MISSED_TYPE);
            }
        });

        btnRejected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                // cambiamos el cursor
                MainActivity.this.swapCursor(Calls.REJECTED_TYPE);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // recuperamos un cursor a la lista de llamadas y lo asignamos al listView.
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
            cursor = null;
        }
        cursor = getCalls(0);
        adapter.swapCursor(cursor);
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
            cursor = null;
        }
        // liberamos el cursor para evitar mantener recursos cuando se esté en segundo plano
        adapter.swapCursor(null);
    }

    private Cursor getCalls (int type) {
        Uri llamadasUri = CallLog.Calls.CONTENT_URI;
        ContentResolver contentResolver = getContentResolver();

        String [] projection = new String [] {Calls._ID, Calls.NUMBER, Calls.DURATION, Calls.TYPE};

        String selection = null;
        String [] selectionArgs = null;

        if (type == Calls.INCOMING_TYPE || type == Calls.OUTGOING_TYPE || type == Calls.MISSED_TYPE || type == Calls.REJECTED_TYPE ) {
            selection = Calls.TYPE + " = ? ";
            selectionArgs = new String [] {Integer.toString(type)};
        }

        String order = Calls.DATE + " DESC ";

        // verificar permisos de llamada
        int permissionCheck = ContextCompat.checkSelfPermission (this, Manifest.permission.READ_CALL_LOG);

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            cursor = contentResolver.query(llamadasUri,   // URI del Content provider
                    projection,    // Columnas a devolver
                    selection,     // Condición a la query
                    selectionArgs, // Argumentos variables de la condición
                    order);         // Orden de los resultados
        }

        return cursor;

    }

    private void swapCursor (int type) {

        Log.d("APPTEST", "Tipo llamada " + type);

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
            cursor = null;
        }
        cursor = getCalls(type);
        adapter.swapCursor(cursor);
    }
}
