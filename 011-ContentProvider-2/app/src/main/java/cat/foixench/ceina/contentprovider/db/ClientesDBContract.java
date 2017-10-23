package cat.foixench.ceina.contentprovider.db;

import android.provider.BaseColumns;


public class ClientesDBContract implements BaseColumns {
    private ClientesDBContract () {}

    public static final String TABLA_CLIENTES = "Clientes";

    //Nombres de columnas
    public static final String COL_NOMBRE = "nombre";
    public static final String COL_TELEFONO = "telefono";
    public static final String COL_EMAIL = "email";
}
