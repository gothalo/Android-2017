package cat.foixench.ceina.notebook.util;

import android.text.format.DateUtils;

import java.util.Date;

/**
 * Contiene constantes y funciones estáticas comunes de la aplicación
 */
public class AppConstants {
    /**
     * TAG para los logs de la aplicación
     */
    public static final String APP_TAG = "NOTEBOOK";

    /**
     * Extras entre las dos activityes.
     */
    public static final String EXTRA_NOTE_ID = "EXTRA_NOTE_ID";

    /**
     * Resultado de la activity edicion de notas. nota borrada
     */
    public static final int RETURN_CODE_NOTE_DELETED = 1;
    public static final int RETURN_CODE_NOTE_ADDED = 2;
    public static final int RETURN_CODE_NOTE_EDITED = 3;
    public static final int RETURN_CODE_CANCELED = 4;

    /**
     * Request code for edit app
     */
    public static final int REQUEST_CODE_EDIT = 1;
    public static final int REQUEST_CODE_ADD = 2;

    /**
     * Recupera la fecha como cadena de texto relativa
     * @param date fecha a convertir
     * @return fecha convertida
     */
    public static String getStringDate  (Date date) {
        return  DateUtils.getRelativeTimeSpanString (date.getTime(), System.currentTimeMillis(), DateUtils.FORMAT_ABBREV_RELATIVE ).toString();
    }
}
