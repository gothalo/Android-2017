package cat.foixench.ceina.notebook.adapters;

import android.content.Context;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.Date;

import cat.foixench.ceina.notebook.R;
import cat.foixench.ceina.notebook.db.DataBaseContract.NoteTable;
import cat.foixench.ceina.notebook.util.AppConstants;

/**
 * Adapter para mostrar las notas el el listado de la aplicación
 */

public class NotesAdapter extends SimpleCursorAdapter {
    /**
     * esta matriz indica los campos a mostrar en el list item
     */
    private static final String [] FROM = new String[] {NoteTable._ID, NoteTable.COLUMN_TITLE, NoteTable.COLUMN_UPDATE_DATE, NoteTable.COLUMN_CONTENT };
    /**
     * esta matriz indica las views donde se han de mostrar los elementos de la nota
     */
    private static final int [] TO = new int [] {R.id.liNoteId, R.id.liNoteTitle, R.id.liNoteDate, R.id.liNoteContent};

    /**
     * Contexto donde se ejecuta este adapter
     */
    private Context context;

    /**
     * Crea una instancia nueva de este adaptador
     * @param context contexto donde se ejecuta este adaptador
     */
    public NotesAdapter(Context context) {

        super (context, R.layout.note_list_item, null, FROM, TO, FLAG_REGISTER_CONTENT_OBSERVER);

        this.context = context;
    }

    /**
     * Se de formatear el contenido de las distintas views de la lista. Modificaremos el texto de la fecha,
     * para que se vea con un formato más legible y el contendio de la nota para mostrar un preview.
     * @param v vista
     * @param text texto a modificar.
     */
    @Override
    public void setViewText(TextView v, String text) {
        switch (v.getId())
        {
            case R.id.liNoteDate :
                text = AppConstants.getStringDate(new Date(Long.parseLong(text)));
                break;
            case R.id.liNoteContent :
                if (text.length() > 100) {
                    text = text.substring(0, 99);
                    text = text + " ... ";
                }
        }
        super.setViewText(v, text);
    }
}
