package cat.foixench.ceina.contentprovider.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import cat.foixench.ceina.contentprovider.db.ClientesDBContract;
import cat.foixench.ceina.contentprovider.db.ClientesSQLiteHelper;


public class ClientesProvider extends ContentProvider {

    private static final String uri = "content://cat.foixench.ceina.contentprovider/clientes";

    /**
     * Uri del proveedor de contenidos
     */
    public static final Uri CONTENT_URI = Uri.parse(uri);

    // necesario para el UriMatcher
    //Necesario para UriMatcher
    private static final int CLIENTES = 1;
    private static final int CLIENTES_ID = 2;
    private static final UriMatcher uriMatcher;

    //Inicializamos el UriMatcher
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI("cat.foixench.ceina.contentprovider", "clientes", CLIENTES);
        uriMatcher.addURI("cat.foixench.ceina.contentprovider", "clientes/#", CLIENTES_ID);
    }

    //Base de datos
    private ClientesSQLiteHelper clidbh;

    /**
     * creación del proveedor de contenidos. se recupera un objeto que instancia la base de datos.
     * @return true si el content provider se ha creado correctamente
     */
    @Override
    public boolean onCreate() {

        clidbh = new ClientesSQLiteHelper(getContext());
        return true;
    }

    /**
     * Lee la información de la base de datos
     * @param uri Uri del content provider
     * @param projection Columnas a devolver de la tabla clientes.
     * @param selection filtro en la consulta. Si la Uri contiene un id se usa el id
     * @param selectionArgs argumentos de la seleccón
     * @param sortOrder ordenación de los resultados
     * @return cursor con los datos devueltos por la consulta
     */
    @Override
    public Cursor query(Uri uri, String[] projection,
                        String selection, String[] selectionArgs, String sortOrder) {

        //Si es una consulta a un ID concreto construimos el WHERE
        String where = selection;
        if (uriMatcher.match(uri) == CLIENTES_ID) {
            where = "_id=" + uri.getLastPathSegment();
        }

        SQLiteDatabase db = clidbh.getWritableDatabase();

        Cursor c = db.query(ClientesDBContract.TABLA_CLIENTES, projection, where,
                selectionArgs, null, null, sortOrder);

        return c;
    }


    /**
     * inserta un registro en la tabla de clientes
     * @param uri uri del conent provider
     * @param values contentvalues con los valores a insertar en el nuevo registor
     * @return uri al nuevo registro
     */
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long regId = 1;

        SQLiteDatabase db = clidbh.getWritableDatabase();

        regId = db.insert(ClientesDBContract.TABLA_CLIENTES, null, values);

        // creamos la uri que apunte al registro recién creado.
        Uri newUri = ContentUris.withAppendedId(CONTENT_URI, regId);

        return newUri;
    }

    /**
     * Actualiza registros de la tabla de clientes
     * @param uri uri del content provider.
     * @param values ContentValues con los valores a actualizar
     * @param selection filtro de actualización.
     * @param selectionArgs argumentos del filtro
     * @return número de registros actualizados por la operación
     */
    @Override
    public int update(Uri uri, ContentValues values,
                      String selection, String[] selectionArgs) {

        int cont;

        //Si es una consulta a un ID concreto construimos el WHERE
        String where = selection;
        if(uriMatcher.match(uri) == CLIENTES_ID){
            where = "_id=" + uri.getLastPathSegment();
        }

        SQLiteDatabase db = clidbh.getWritableDatabase();

        cont = db.update(ClientesDBContract.TABLA_CLIENTES, values, where, selectionArgs);

        return cont;
    }

    /**
     * borra registros de la tabla clientes
     * @param uri uri del proveedor de contenidos
     * @param selection flitro en el borrado. si se indica una uri que apunte a un registro concreto se borrara dicho registro
     * @param selectionArgs argumentos en el filtro de borrado
     * @return numero de registros eliminados
     */

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        int cont;

        //Si es una consulta a un ID concreto construimos el WHERE
        String where = selection;
        if(uriMatcher.match(uri) == CLIENTES_ID){
            where = "_id=" + uri.getLastPathSegment();
        }

        SQLiteDatabase db = clidbh.getWritableDatabase();

        cont = db.delete(ClientesDBContract.TABLA_CLIENTES, where, selectionArgs);

        return cont;
    }

    /**
     * devuelve el mime type asociado a las uri (cursor/cliente) del content provider
     * @param uri
     * @return
     */
    @Override
    public String getType(Uri uri) {
        int match = uriMatcher.match(uri);

        switch (match)
        {
            case CLIENTES:
                return "vnd.android.cursor.dir/vnd.ceina.cliente";
            case CLIENTES_ID:
                return "vnd.android.cursor.item/vnd.ceina.cliente";
            default:
                return null;
        }
    }

}
