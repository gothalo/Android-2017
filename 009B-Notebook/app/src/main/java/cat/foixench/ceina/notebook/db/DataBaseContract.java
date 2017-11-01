package cat.foixench.ceina.notebook.db;

import android.provider.BaseColumns;

/**
 * Contiene constantes para describir las tablas de la aplicación
 */

public class DataBaseContract {

    private DataBaseContract() {

    }

    /**
     * clase que describre la tabla NOTES de la base de datos
     */
    public static class NoteTable implements BaseColumns {
        private NoteTable () {

        }

        /**
         * Nombre de la tabla NOTES
         */
        public static final String TABLE_NAME = "NOTES";
        /**
         * Columna TITLE de la tabla NOTES
         */
        public static final String COLUMN_TITLE = "TITLE";
        /**
         * Columna CONTENT de la tabla NOTES
         */
        public static final String COLUMN_CONTENT = "CONTENT";
        /**
         * Columna CREATION_DATE de la tabla NOTES
         */
        public static final String COLUMN_CREATION_DATE = "CREATION_DATE";
        /**
         * Columna UPDATE_DATE de la tabla NOTES
         */
        public static final String COLUMN_UPDATE_DATE = "UPDATE_DATE";
    }
}
