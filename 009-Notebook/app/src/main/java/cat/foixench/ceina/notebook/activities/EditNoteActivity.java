package cat.foixench.ceina.notebook.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;

import cat.foixench.ceina.notebook.R;
import cat.foixench.ceina.notebook.data.Note;
import cat.foixench.ceina.notebook.db.DataBaseHelper;
import cat.foixench.ceina.notebook.util.AppConstants;

/**
 * activity creada para añadir / editar una nota
 */

public class EditNoteActivity extends AppCompatActivity {

    private EditText etNoteTitle, etNoteContent;
    private TextView tvNoteId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_note_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // recuperamos referencias a los controles
        tvNoteId = (TextView) findViewById(R.id.tvNoteId);
        etNoteContent = (EditText) findViewById(R.id.etNoteContent);
        etNoteTitle = (EditText) findViewById(R.id.etNoteTitle);

        Intent intent = getIntent();
        // procesamos la recepción de una nota para editar
        if (intent != null && intent.hasExtra(AppConstants.EXTRA_NOTE_ID)) {
            long id = intent.getLongExtra(AppConstants.EXTRA_NOTE_ID, 0);
            if (id > 0) {
                loadAndShowNote (id);
            }
        }

        // procesamos la recepción de una llamada desde otra aplicación para añadir una nueva nota
        if (intent.ACTION_SEND.equals(intent.getAction()) ) {
            String type = intent.getType();
            if (type != null && type.equals("text/plain")) {
                String sharedText = intent.getStringExtra (Intent.EXTRA_TEXT);
                if (sharedText != null) {
                    etNoteContent.setText(sharedText);
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        long noteId = tvNoteId.getText() != null && !tvNoteId.getText().toString().trim().equals( "") ? Long.parseLong(tvNoteId.getText().toString()) : 0;
        Intent resultIntent;

        switch (item.getItemId())
        {
            case R.id.mniSaveNote :
                long newNoteId = saveNote(noteId);
                if (newNoteId > 0) {
                    resultIntent = new Intent ();
                    resultIntent.putExtra(AppConstants.EXTRA_NOTE_ID, newNoteId);
                    setResult(noteId == 0 ? AppConstants.RETURN_CODE_NOTE_ADDED : AppConstants.RETURN_CODE_NOTE_EDITED, resultIntent);
                    finish();
                }
                return true;
            case R.id.mniDeleteNote :
                // borrar la nota
                if (deleteNote (noteId)) {
                    // cerrar la app
                    resultIntent = new Intent();
                    setResult(AppConstants.RETURN_CODE_NOTE_DELETED, resultIntent);
                    finish();
                }
                return true;
            case android.R.id.home :
                this.setResult(AppConstants.RETURN_CODE_CANCELED);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean deleteNote (long noteId) {
        if (noteId > 0) {
            DataBaseHelper dbHelper = new DataBaseHelper(this);
            return dbHelper.deleteNote(noteId) == 1;
        }
        return false;

    }

    private long saveNote (long noteId) {
        long returnValue = 0;
        DataBaseHelper dbHelper = new DataBaseHelper(this);
        String title = etNoteTitle.getText().toString();
        String content = etNoteContent.getText().toString();
        Note note = new Note (noteId, title, content, new Date (System.currentTimeMillis()), new Date(System.currentTimeMillis()));

        if (noteId > 0) {
            // nota existente. la guardamos.
            dbHelper.updateNote(note);
            returnValue = noteId;
        } else {
            returnValue = dbHelper.addNote(note);
        }

        return returnValue;
    }

    private void loadAndShowNote (long id) {
        // cargar la nota de BBDD
        DataBaseHelper dbHelper = new DataBaseHelper(this);
        Note note = dbHelper.getNote(id);

        if (note != null) {
            tvNoteId.setText(Long.toString(note.getId()));
            etNoteContent.setText (note.getContent());
            etNoteTitle.setText(note.getTitle());

        }

    }
}
