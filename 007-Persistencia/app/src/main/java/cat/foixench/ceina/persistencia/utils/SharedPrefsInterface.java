package cat.foixench.ceina.persistencia.utils;

/**
 * Created by llorenc on 12/10/2017.
 */

public interface SharedPrefsInterface {

    public static final String APP_TAG = "LF_PERSISTENCIA";
    public static final String APP_PREFERENCES = "prefs";

    public static final String INTENT_EXTRA_ACTIVITY_LAUNCH = "CallerActivity";
    public static final String INTENT_EXTRA_ACTIVITY_LAUNCH_VALUE_LAUNCH = "LaunchActivity";
    public static final String DEFAULT_VALUE_EMPTY = "";

    public static final String PARAM_USER_NAME ="UserName";
    public static final String PARAM_USE_SD = "SaveToSD";

    public static final String APP_DATA_FILE = "persistencia.txt";
    public static final String APP_DATA_FOLDER = "persistencia";

    public enum SD_State {
        OK,
        READ_ONLY,
        NONE;
    }

}
