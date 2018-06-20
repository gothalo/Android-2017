package cat.foixench.ceina.notebook.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import cat.foixench.ceina.notebook.R;
import cat.foixench.ceina.notebook.data.Note;
import cat.foixench.ceina.notebook.db.DataBaseHelper;
import cat.foixench.ceina.notebook.util.AppConstants;

public class ViewNoteActivity extends AppCompatActivity {
    private TextView tvHeader, tvContent, tvCreated, tvUpdated, tvId;

    /**
     * creación de la activity
     * @param savedInstanceState Bundle con datos guardados de la activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_note_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tvHeader = (TextView) findViewById(R.id.tvNoteTile);
        tvContent = (TextView) findViewById(R.id.tvNoteContent);
        tvCreated = (TextView) findViewById(R.id.tvNoteDateCreated);
        tvUpdated = (TextView) findViewById(R.id.tvNoteDateUpdated);
        tvId = (TextView) findViewById(R.id.tvNoteId);

        // recuperamos el Id de la nota a visualizar
        if (getIntent().hasExtra(AppConstants.EXTRA_NOTE_ID)) {
            long idNote = getIntent().getLongExtra(AppConstants.EXTRA_NOTE_ID, 0);

            if (idNote > 0) {
                loadAndShowNote (idNote);
            }
        }

        TextView tvNote = (TextView) findViewById(R.id.tvNoteContent);
        if (tvNote != null) {
            tvNote.setMovementMethod(new ScrollingMovementMethod());
        }

    }

    /**
     * procesa el retorno del borrado de una nota.
     * @param requestCode codigo con el que se llama a la activity nueva
     * @param resultCode resultado de ejecución de la activity
     * @param data datos adicionales en forma de intent
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AppConstants.REQUEST_CODE_EDIT) {
            switch (resultCode) {
                case AppConstants.RETURN_CODE_NOTE_DELETED :
                    Toast.makeText(this, R.string.delete_note_ok, Toast.LENGTH_LONG).show();
                    this.finish();
                    break;
                case AppConstants.RETURN_CODE_NOTE_EDITED :
                    long idNote = data.getLongExtra(AppConstants.EXTRA_NOTE_ID, 0);
                    loadAndShowNote(idNote);
                    Toast.makeText(this, R.string.edit_note_ok, Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mniEditNote:
                Intent intent = new Intent(ViewNoteActivity.this, EditNoteActivity.class);
                intent.putExtra(AppConstants.EXTRA_NOTE_ID, Long.parseLong(tvId.getText().toString()));
                startActivityForResult(intent, AppConstants.REQUEST_CODE_EDIT);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * carga el contendio de una nota y la muestra por pantalla.
     * @param idNote identifiador de la nota a cargar
     */
    private void loadAndShowNote (long idNote) {
        DataBaseHelper db = new DataBaseHelper(this);
        Note note = db.getNote(idNote);

        tvHeader.setText(note.getTitle());
        tvContent.setText(note.getContent());
        tvCreated.setText(AppConstants.getStringDate(note.getCreationDate()));
        tvUpdated.setText(AppConstants.getStringDate(note.getUpdateDate()));
        tvId.setText(Long.toString(note.getId()));
    }
}
