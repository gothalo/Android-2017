package cat.foixench.ceina.registrodellamadas.utils;

import java.util.Locale;

/**
 * Created by llorenc on 22/10/2017.
 */

public class AppUtils {

    public static String secondsToHoutMinSecond (long seconds) {

        int hours = (int) seconds / 3600;
        int remainder = (int) seconds - hours * 3600;
        int mins = remainder / 60;
        remainder = remainder - mins * 60;
        int secs = remainder;

        return String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, mins, secs);

    }

}
