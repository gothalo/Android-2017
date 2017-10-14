package cat.foixench.ceina.persistencia.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.ExifInterface;
import android.os.Environment;

/**
 * Created by llorenc on 12/10/2017.
 */

public class AppUtils implements SharedPrefsInterface {

    /**
     * lee y retorna una preferencia de tipo string
     * @param context
     * @param key
     * @return
     */
    public static String getStringPreference (Context context, String key) {
        // recuperamos las preferencias del sistema
        SharedPreferences prefs = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        String pref = prefs.getString(key, DEFAULT_VALUE_EMPTY);
        // retornamos la preferencia
        return pref;
    }

    /**
     * Lee y returna una preferencia de tipo booleano
     * @param context
     * @param key
     * @return si no existe, retorna false
     */
    public static boolean getBooleanPreference (Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        boolean pref = prefs.getBoolean(key, false);
        // retornamos la preferencia
        return pref;

    }

    /**
     * Veriica el estado de la tarjeta de memoria
     * @return SD_State.OK si esta presente, SD_State.READ_ONLY si es solo lectura, SD_State.NONE si no es accesible
     */
    public static SD_State isSDCardEnabled () {
        // miramos el estado del almacenamiento externo
        String extStorageState = Environment.getExternalStorageState();

        if (extStorageState.equals(Environment.MEDIA_MOUNTED)) {
            return SD_State.OK;
        } else if (extStorageState.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
            return SD_State.READ_ONLY;
        } else {
            return SD_State.NONE;
        }
    }

    public static boolean hasPermisionToWriteSD (Context context) {
        String permission = "android.permission.WRITE_EXTERNAL_STORAGE";
        int res = context.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }
}
