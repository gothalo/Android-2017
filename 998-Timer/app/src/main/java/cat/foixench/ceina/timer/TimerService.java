package cat.foixench.ceina.timer;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by llorenc on 13/11/2017.
 */

public class TimerService extends Service {

    private final IBinder timerServiceBinder = new TimerServiceBinder();
    private long startTime = 0;
    private long endTime = 0;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return timerServiceBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        startTime = 0;
        endTime = 0;
    }

    public class TimerServiceBinder extends Binder
    {
        public TimerService getService () {
            return TimerService.this;
        }
    }

    public void start () {
        this.startTime = System.currentTimeMillis();
    }

    public long stop () {
        this.endTime = System.currentTimeMillis();
        return endTime - startTime;
    }
}
