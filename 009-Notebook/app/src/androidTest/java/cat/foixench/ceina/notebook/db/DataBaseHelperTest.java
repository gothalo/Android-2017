package cat.foixench.ceina.notebook.db;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import cat.foixench.ceina.notebook.data.Note;
import cat.foixench.ceina.notebook.util.AppConstants;

import static org.junit.Assert.*;

/**
 * test de acceso a la base de datos
 */
public class DataBaseHelperTest {
    private Context context;
    private DataBaseHelper db;

    @Before
    public void setUp() throws Exception {
        context = InstrumentationRegistry.getTargetContext();
        db = new DataBaseHelper(context);
    }

    @Test
    public void getNote() throws Exception {
        Note firstNote = db.getNote(1);
        assertNotNull(firstNote);
        Log.d(AppConstants.APP_TAG, "[" + AppConstants.getStringDate(firstNote.getCreationDate()) + "]");

    }

    @Test
    public void addNote() throws Exception {

        Date updateDate = new Date (System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(updateDate);
        calendar.add(Calendar.YEAR, -1);

        Note newNote = new Note(0,
                                "Test Note",
                                "Contenido de la nota de pruebas. Esta nota contiene más información que las anteriores, por lo que será más larga",
                                new Date(System.currentTimeMillis()),
                                new Date (calendar.getTimeInMillis()));

        long newId = db.addNote(newNote);

        Log.d(AppConstants.APP_TAG, "Id del nuevo registro : [" + newId + "]");
        assertTrue(newId > 0);
    }

    @Test
    public void deleteNote () throws Exception {
        int rowsDeleted = db.deleteNote(2);

        Log.d(AppConstants.APP_TAG, "Registros eliminaos : [" + rowsDeleted + "]");

        assertEquals(1, rowsDeleted);
    }

}