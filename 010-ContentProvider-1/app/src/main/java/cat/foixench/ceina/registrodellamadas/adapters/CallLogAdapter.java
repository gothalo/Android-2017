package cat.foixench.ceina.registrodellamadas.adapters;

import android.content.Context;
import android.icu.util.TimeZone;
import android.provider.CallLog;
import android.provider.CallLog.Calls;
import android.util.Log;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import cat.foixench.ceina.registrodellamadas.R;
import cat.foixench.ceina.registrodellamadas.utils.AppUtils;


/**
 * Created by llorenc on 22/10/2017.
 */

public class CallLogAdapter extends SimpleCursorAdapter {

    private static final String [] FROM = new String[] { Calls.NUMBER, Calls.DURATION, Calls.TYPE};
    private static final int [] TO = new int [] { R.id.liCallNumber, R.id.liCallDuration, R.id.liCallIcon};


    public CallLogAdapter (Context context) {
        super (context, R.layout.list_item_call, null, FROM, TO, SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
    }

    @Override
    public void setViewImage(ImageView view, String value) {
        if (view.getId() == R.id.liCallIcon) {
            switch (Integer.parseInt (value)) {
                case Calls.INCOMING_TYPE :
                    view.setImageResource(R.drawable.ic_recived);
                    break;
                case Calls.OUTGOING_TYPE :
                    view.setImageResource(R.drawable.ic_made);
                    break;
                case Calls.MISSED_TYPE :
                    view.setImageResource(R.drawable.ic_missed);
                    break;
                case Calls.REJECTED_TYPE :
                    view.setImageResource(R.drawable.ic_rejected);
                    break;
            }
            Log.d("APPTEST", value);
        }
    }

    @Override
    public void setViewText(TextView view, String text) {
        // para la duración, realizaremos un formateo
        if (view.getId() == R.id.liCallDuration) {
            long longVal = Long.parseLong(text);
            text = AppUtils.secondsToHoutMinSecond(longVal);
        }
        super.setViewText(view, text);

    }
}
