package cat.foixench.ceina.sqlite.db;


import android.provider.BaseColumns;

/**
 * Clase para definir constantes de base de datos
 */
public class DBContract {

    /**
     * Esta clase contiene solo constantes públicas de acceso a la base de datos. Creamos un constructor privado
     * para evitar instancais de la base de datos
     */
    private DBContract () {

    }

    public static class ShopTable implements BaseColumns {
        private ShopTable () {

        }

        public static final String TABLE_NAME = "SHOPS";

        public static final String COLUMN_NAME = "NAME";
        public static final String COLUMN_ADDRESS = "ADDRESS";
    }
}
