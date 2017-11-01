package cat.foixench.ceina.notebook.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Date;

import cat.foixench.ceina.notebook.data.Note;
import cat.foixench.ceina.notebook.db.DataBaseContract.NoteTable;
import cat.foixench.ceina.notebook.util.AppConstants;

/**
 * Clase que se encarga de la creación y el acceso a la base de datos
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Notedb.db";
    private static final int DATABASE_VERSION = 1;

    /**
     * Crea una instancia de la clase asociada al contexto
     * @param context contexto donde se ejecuta la app
     */
    public DataBaseHelper(Context context) {
        super (context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Creación del contenido de la base de datos en su primera ejecución
     * @param sqLiteDatabase base de datos de la aplicación
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // creación de la tabla notas
        String createNotesTable = "CREATE TABLE " + NoteTable.TABLE_NAME
                                + "("
                                + " " + NoteTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                                + " " + NoteTable.COLUMN_TITLE + " TEXT NOT NULL, "
                                + " " + NoteTable.COLUMN_CONTENT + " TEXT NOT NULL, "
                                + " " + NoteTable.COLUMN_CREATION_DATE + " DATE, "
                                + " " + NoteTable.COLUMN_UPDATE_DATE + " DATE "
                                + ")";

        sqLiteDatabase.execSQL(createNotesTable);

    }

    /**
     * Actualización de la base de datos
     * @param sqLiteDatabase base de datos de la aplicacion
     * @param oldVersion versión anterior de la bbdd
     * @param newVersion versión actual de la base de datos
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // versión 1 de la app. no hay nada que actualizar
    }

    /**
     * Recupera el detalle de una nota
     * @param id id del regsitro a recuperar
     * @return detalles de la nota, null si no se encuentra
     */
    public Note getNote (long id) {
        Note note = null;
        try {
            SQLiteDatabase db = this.getReadableDatabase();


            String[] columns = new String [] { NoteTable._ID,
                                               NoteTable.COLUMN_TITLE,
                                               NoteTable.COLUMN_CONTENT,
                                               NoteTable.COLUMN_CREATION_DATE,
                                               NoteTable.COLUMN_UPDATE_DATE};

            String selection = NoteTable._ID + " = ? ";
            String[] selectionArgs = new String [] {Long.toString(id)};
            Cursor cursor = db.query(NoteTable.TABLE_NAME, columns, selection, selectionArgs, null, null, null);

            if (cursor != null) {
                if (cursor.moveToNext ()) {
                    note = new Note (cursor.getLong (cursor.getColumnIndex(NoteTable._ID)),
                                     cursor.getString (cursor.getColumnIndex(NoteTable.COLUMN_TITLE)),
                                     cursor.getString (cursor.getColumnIndex(NoteTable.COLUMN_CONTENT)),
                                     new Date (cursor.getLong (cursor.getColumnIndex(NoteTable.COLUMN_CREATION_DATE))),
                                     new Date (cursor.getLong (cursor.getColumnIndex(NoteTable.COLUMN_UPDATE_DATE))));

                }
                // liberamos y cerramos el cursor.
                cursor.close();
            }

        } catch (Exception ex) {
            Log.e (AppConstants.APP_TAG, "Error en getNote", ex);
        }

        return note;
    }

    /**
     * Cierra la base de datos
     * @param db conexión a base de dato a cerrar
     */
    public void closeDatabase (SQLiteDatabase db) {
        if (db != null && db.isOpen()) {
            db.close();
        }
    }

    /**
     * Recupera un cursor con todas las notas.
     * @return Cursor con el contendio de todas las notas
     */
    public Cursor getAllNotes (SQLiteDatabase db) {

        String[] columns = new String [] { NoteTable._ID,
                NoteTable.COLUMN_TITLE,
                NoteTable.COLUMN_CONTENT,
                NoteTable.COLUMN_CREATION_DATE,
                NoteTable.COLUMN_UPDATE_DATE};
        String orderBy = NoteTable.COLUMN_UPDATE_DATE + " DESC ";

        return db.query(NoteTable.TABLE_NAME, columns, null, null, null, null, orderBy);
    }

    /**
     * guarda una nota nueva en base de datos
     * @param newNote la información de la nota nueva
     * @return el identificador de la nueva nota
     */
    public long addNote (Note newNote) {
        long newId = -1;

        try {
            SQLiteDatabase db = this.getWritableDatabase();


            ContentValues contentValues = new ContentValues();

            contentValues.put(NoteTable.COLUMN_CONTENT, newNote.getContent());
            contentValues.put(NoteTable.COLUMN_TITLE, newNote.getTitle());
            contentValues.put(NoteTable.COLUMN_CREATION_DATE, newNote.getCreationDate().getTime());
            contentValues.put(NoteTable.COLUMN_UPDATE_DATE, newNote.getUpdateDate().getTime());

            newId = db.insert(NoteTable.TABLE_NAME, null, contentValues);

        } catch (Exception ex) {
            Log.e (AppConstants.APP_TAG, "Error en addNote", ex);
        }

        return newId;
    }

    /**
     * Elimina la nota cuyo id se pasa como parametro
     * @param id identificador de la nota a borrar
     * @return numero de registros borrados. esperaremos 1 si se ha borrado un registro o 0 si no se ha borrado ninguno
     */
    public int deleteNote (long id) {
        SQLiteDatabase db = getWritableDatabase();

        String where = NoteTable._ID + " = ?";
        String [] whereArgs = new String [] {Long.toString(id)};

        int rowsDeleted = db.delete(NoteTable.TABLE_NAME, where, whereArgs);

        db.close();

        return rowsDeleted;
    }

    /**
     * Actualiza los contenidos de una nota
     * @param note datos de la nota a actualizar
     * @return 1 si se ha actualizado la nota, 0 si no se ha localizado el id de la nota a actualizar en base de datos
     */
    public int updateNote (Note note) {
        int rowsUpdated = 0;

        try {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();

            values.put(NoteTable.COLUMN_CONTENT, note.getContent());
            values.put(NoteTable.COLUMN_TITLE, note.getTitle());
            values.put(NoteTable.COLUMN_UPDATE_DATE, System.currentTimeMillis());

            String where = NoteTable._ID + " = ? ";
            String [] whereArgs = new String []{Long.toString(note.getId())};

            rowsUpdated = db.update(NoteTable.TABLE_NAME, values, where, whereArgs);

            db.close();

        } catch (Exception ex) {
            Log.e(AppConstants.APP_TAG, "Error en updateNote", ex);
        }

        return rowsUpdated;
    }


}
