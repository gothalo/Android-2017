package cat.foixench.ceina.notebook.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import cat.foixench.ceina.notebook.R;
import cat.foixench.ceina.notebook.activities.ViewNoteActivity;
import cat.foixench.ceina.notebook.adapters.NotesAdapter;
import cat.foixench.ceina.notebook.db.DataBaseHelper;
import cat.foixench.ceina.notebook.util.AppConstants;

/**
 * Created by llorenc on 30/10/2017.
 */

public class NotesListFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_fragment_layout, container, false);
    }

    private NotesAdapter adapter;
    private SQLiteDatabase db;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

       // creamos un adaptador para la lista
        adapter = new NotesAdapter(getActivity());
        ListView lstNotes = (ListView) getView().findViewById(R.id.lstNotes);

        lstNotes.setAdapter(adapter);

        lstNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                TextView tvNote = view.findViewById(R.id.liNoteId);
                if (tvNote != null) {

                    long noteId = Long.parseLong(tvNote.getText().toString());

                    FragmentManager fragmentManager = getFragmentManager();
                    NoteDetailFragment detailFragment = (NoteDetailFragment) fragmentManager.findFragmentById(R.id.detailFragment);

                    if (detailFragment != null) {
                        detailFragment.setNoteId(noteId);
                    } else {

                        Intent intent = new Intent(getActivity(), ViewNoteActivity.class);
                        // pasamos el id a la tabla
                        intent.putExtra(AppConstants.EXTRA_NOTE_ID, noteId);

                        startActivity(intent);
                    }
                }
            }
        });
    }

    /**
     * en este metodo inicializamos abrimos el cursor a la base de datos.
     * @see android.app.Activity#onStart()
     */
    @Override
    public void onStart() {
        super.onStart();
        DataBaseHelper dbHelper = new DataBaseHelper(getActivity());
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
    public void onStop() {
        super.onStop();
        adapter.swapCursor(null);
    }

    /**
     * al cerrar la activity definiivamente miramos que no tengamos la base de datos abierta.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();

        if (db != null && db.isOpen()) {
            DataBaseHelper dbHelper = new DataBaseHelper(getActivity());
            dbHelper.closeDatabase(db);
        }
    }

 /**
     * Procesa el resultado del proceso de añadir una nota, creada a partir de la llamada startActivityForResult
     * @param requestCode código de la petición realizada al llamar a la nueva activity
     * @param resultCode resultado de la ejecución de la llamada a la activity
     * @param data Intent con información de la activity creada.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AppConstants.REQUEST_CODE_ADD) {
            switch (resultCode) {
                case AppConstants.RETURN_CODE_NOTE_ADDED :
                    Toast.makeText(getActivity(), R.string.add_note_ok, Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }


}
