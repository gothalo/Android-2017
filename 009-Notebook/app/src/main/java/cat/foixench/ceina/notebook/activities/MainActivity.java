package cat.foixench.ceina.notebook.activities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import cat.foixench.ceina.notebook.R;
import cat.foixench.ceina.notebook.db.DataBaseHelper;
import cat.foixench.ceina.notebook.util.AppConstants;
import cat.foixench.ceina.notebook.adapters.NotesAdapter;

public class MainActivity extends AppCompatActivity {

    private NotesAdapter adapter;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // creamos un adaptador para la lista
        adapter = new NotesAdapter(this);
        ListView lstNotes = (ListView) findViewById(R.id.lstNotes);

        lstNotes.setAdapter(adapter);

        lstNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                TextView tvNote = view.findViewById(R.id.liNoteId);
                if (tvNote != null) {

                    long noteId = Long.parseLong(tvNote.getText().toString());

                    Intent intent = new Intent (MainActivity.this, ViewNoteActivity.class);
                    // pasamos el id a la tabla
                    intent.putExtra(AppConstants.EXTRA_NOTE_ID, noteId);

                    startActivity(intent);
                }
            }
        });
    }

    /**
     * en este metodo inicializamos abrimos el cursor a la base de datos.
     * @see android.app.Activity#onStart()
     */
    @Override
    protected void onStart() {
        super.onStart();
        DataBaseHelper dbHelper = new DataBaseHelper(this);
        if (db == null || !db.isOpen()) {
            db = dbHelper.getReadableDatabase();
        }
        adapter.swapCursor(dbHelper.getAllNotes (db));
    }

    /**
     * en este mŽtodo liveramos el cursor, para que cuando la app pase a segundo plano, no consuma recursos extras.
     * @see android.app.Activity#onStop()
     */
    @Override
    protected void onStop() {
        super.onStop();
        adapter.swapCursor(null);
    }

    /**
     * al cerrar la activity definiivamente miramos que no tengamos la base de datos abierta.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (db != null && db.isOpen()) {
            DataBaseHelper dbHelper = new DataBaseHelper(this);
            dbHelper.closeDatabase(db);
        }
    }

    /**
     * añadimos el menu de opciones a la toolbar
     * @param menu menu de la tooblar
     * @return true para mostra el menú en la toolbar.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    /**
     * procesa los eventos relacionados con el menú de esta activity
     * @param item elemento de menu seleccionado
     * @return true si la opción del menú se ha procesado aquí. falso si debe usar el comportamiento por defecto
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mniAddNote :
                Intent intent = new Intent(MainActivity.this, EditNoteActivity.class);
                startActivityForResult(intent, AppConstants.REQUEST_CODE_ADD);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Procesa el resultado del proceso de añadir una nota, creada a partir de la llamada startActivityForResult
     * @param requestCode código de la petición realizada al llamar a la nueva activity
     * @param resultCode resultado de la ejecución de la llamada a la activity
     * @param data Intent con información de la activity creada.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AppConstants.REQUEST_CODE_ADD) {
            switch (resultCode) {
                case AppConstants.RETURN_CODE_NOTE_ADDED :
                    Toast.makeText(this, R.string.add_note_ok, Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }

}
