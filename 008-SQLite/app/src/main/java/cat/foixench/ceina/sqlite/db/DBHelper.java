package cat.foixench.ceina.sqlite.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import cat.foixench.ceina.sqlite.data.Shop;
import cat.foixench.ceina.sqlite.db.DBContract.ShopTable;

/**
 * Helper para acceder a la base de datos de la aplicación
 */

public class DBHelper extends SQLiteOpenHelper {
    /**
     * Permite controlar la versión de la base de datos
     */
    private final static int DATABASE_VERSION = 1;
    /**
     * Define el nombre de la base de datos
     */
    public final static String DATABASE_NAME = "myDataBase";

    /**
     * Crea una instancia de la clase
     * @param context contexto donde se ejecuta la aplicacion
     */
    public DBHelper (Context context) {
        // llamada al constructor de clase padre, indicando la versión de la base de datos
        super (context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Script de creación de la base de datos
     * @param sqLiteDatabase Base de datos que se debe crear
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // creación de la tabla Shops
        String createShopTable = "CREATE TABLE " + ShopTable.TABLE_NAME
                               + " (" + ShopTable._ID  + " INTEGER PRIMARY KEY AUTOINCREMENT "
                               + " ," + ShopTable.COLUMN_NAME + " TEXT "
                               + " ," + ShopTable.COLUMN_ADDRESS + " TEXT "
                               + " )";

        // lanzamos la query
        sqLiteDatabase.execSQL(createShopTable);

        // insertamos regitros de prueba
        String insertQuery = "INSERT INTO " + ShopTable.TABLE_NAME
                           + " ("
                           + ShopTable.COLUMN_NAME + ", "
                           + ShopTable.COLUMN_ADDRESS + " "
                           + " )"
                           + " VALUES (?,?)";

        String queryArgs [] = new String [] {"La Botigueta", "Av.Barcelona 12 Baixos 08700 Igualada"};

        sqLiteDatabase.execSQL (insertQuery, queryArgs);

    }

    /**
     * Scrips de actualización de la base de datos
     * @param sqLiteDatabase Base de datos a actualizar
     * @param oldVersion Versión actual de la base de datos
     * @param newVersion Nueva versión de la base de datos
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        switch (oldVersion)
        {
            case 1 :
                // añadir codigo para pasar de la versión 1 de bbdd a la versión 2
            case 2:
                // añadir código para paser de la versión 2 de la bbdd a la versión 3
                // ...
        }
    }

    /**
     * Recupera la tienda con el Id pasado como parámetro.
     * @param id identificador de la tienda
     * @return Datos de la tienda recuperada. null si no hse ha encontrado ninguna
     */
    public Shop getShop (long id) {
        Shop contact = null;

        SQLiteDatabase db = this.getReadableDatabase();
        // columnas que queremos recuperar de la tabla.
        String [] columns = new String [] {ShopTable._ID, ShopTable.COLUMN_NAME, ShopTable.COLUMN_ADDRESS};
        // Sentencia Where
        String selection = ShopTable._ID + " = ?";
        // parametros para la sentencia where;
        String selectionArgs[] = new String[]  {Long.toString(id)};

        Cursor cursor = db.query (ShopTable.TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                contact = new Shop(cursor.getInt(cursor.getColumnIndex(ShopTable._ID))
                        , cursor.getString(cursor.getColumnIndex(ShopTable.COLUMN_NAME))
                        , cursor.getString(cursor.getColumnIndex(ShopTable.COLUMN_ADDRESS)));
            }
            cursor.close();
        }
        // close objects
        db.close();

        return contact;
    }

    /**
     * Recupera todas las tiendas de la base de datos
     */
    public List<Shop> getAllShops () {
        List <Shop> shopsList = new ArrayList<Shop>();

        SQLiteDatabase db = this.getReadableDatabase();
        // columnas que queremos recuperar de la tabla.
        String [] columns = new String [] {ShopTable._ID, ShopTable.COLUMN_NAME, ShopTable.COLUMN_ADDRESS};

        Cursor cursor = db.query (ShopTable.TABLE_NAME, columns, null, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                Shop contact = new Shop(cursor.getInt(cursor.getColumnIndex(ShopTable._ID))
                        , cursor.getString(cursor.getColumnIndex(ShopTable.COLUMN_NAME))
                        , cursor.getString(cursor.getColumnIndex(ShopTable.COLUMN_ADDRESS)));
                shopsList.add(contact);
            }

            cursor.close();
        }
        db.close();

        return shopsList;
    }

    /**
     * Añade una tienda al listado
     * @param shop datos de la tiend a añadir
     * @return id de la nueva tienda
     */
    public long addShop (Shop shop) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ShopTable.COLUMN_ADDRESS, shop.getAddress());
        values.put(ShopTable.COLUMN_NAME, shop.getName());

        long newId =  db.insert(ShopTable.TABLE_NAME, null, values);

        db.close();

        return newId;
    }

    /**
     * actualiza la tienda recibida como parámetro
     * @param shop los datos de la tienda
     * @return numero de registors afectados.
     */
    public int updateShop (Shop shop) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ShopTable.COLUMN_NAME, shop.getName());
        values.put(ShopTable.COLUMN_ADDRESS, shop.getAddress());

        String whereClause = ShopTable._ID + " = ?";
        String args [] = new String [] {Long.toString(shop.getId())};

        int rowsUpdated = db.update(ShopTable.TABLE_NAME, values, whereClause, args);

        db.close ();

        return rowsUpdated;
    }

    public int deleteShop (Shop shop) {
        SQLiteDatabase db = getWritableDatabase();

        String whereClause = ShopTable._ID + " = ? ";
        String args [] = new String [] {Long.toString(shop.getId())};

        int rowsDeleted = db.delete (ShopTable.TABLE_NAME, whereClause, args);

        db.close();

        return rowsDeleted;
    }
}
