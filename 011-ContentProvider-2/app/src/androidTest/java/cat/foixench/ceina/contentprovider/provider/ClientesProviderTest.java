package cat.foixench.ceina.contentprovider.provider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import android.webkit.URLUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import cat.foixench.ceina.contentprovider.db.ClientesDBContract;
import cat.foixench.ceina.contentprovider.util.AppUtils;

import static org.junit.Assert.*;

/**
 * Created by llorenc on 23/10/2017.
 */
@RunWith(AndroidJUnit4.class)
public class ClientesProviderTest {

    private Context context;
    private Uri uriClientes;
    private ContentResolver contentResolver;


    @Before
    public void setUp () throws Exception {
        this.context = InstrumentationRegistry.getTargetContext();
        uriClientes = ClientesProvider.CONTENT_URI;
        contentResolver = this.context.getContentResolver();
    }

    @Test
    public void insert() throws Exception {

        ContentValues values = new ContentValues();

        values.put(ClientesDBContract.COL_NOMBRE, "Noemi");
        values.put(ClientesDBContract.COL_EMAIL, "noemi@gmail.com");
        values.put(ClientesDBContract.COL_TELEFONO, "93 806 00 01");

        Uri newRecordUri = this.contentResolver.insert(this.uriClientes, values);

        try {
            assertTrue (URLUtil.isValidUrl(newRecordUri.toString()));

        } catch (NullPointerException ex) {
            Log.e(AppUtils.APP_TAG, "Error parsing new record uri", ex);
        }

        Cursor cursor = this.contentResolver.query(newRecordUri, null, null, null, null);

        assertNotNull(cursor);

        if (cursor != null) {
            cursor.moveToFirst();
            assertEquals("Noemi", cursor.getString(cursor.getColumnIndex(ClientesDBContract.COL_NOMBRE)));
            assertEquals("noemi@gmail.com", cursor.getString(cursor.getColumnIndex(ClientesDBContract.COL_EMAIL)));
            assertEquals("93 806 00 01", cursor.getString(cursor.getColumnIndex(ClientesDBContract.COL_TELEFONO)));
        }
        cursor.close();

        values = new ContentValues();
        values.put(ClientesDBContract.COL_TELEFONO, "93 805 10 10");

        long records = contentResolver.update(newRecordUri, values, null, null);

        assertEquals(1, records);

        cursor = this.contentResolver.query(newRecordUri, null, null, null, null);

        assertNotNull(cursor);

        if (cursor != null) {
            cursor.moveToFirst();
            assertEquals("Noemi", cursor.getString(cursor.getColumnIndex(ClientesDBContract.COL_NOMBRE)));
            assertEquals("noemi@gmail.com", cursor.getString(cursor.getColumnIndex(ClientesDBContract.COL_EMAIL)));
            assertEquals("93 805 10 10", cursor.getString(cursor.getColumnIndex(ClientesDBContract.COL_TELEFONO)));
        }
        cursor.close();

        records = contentResolver.delete(newRecordUri , null, null);

        assertEquals(1, records);

    }

}