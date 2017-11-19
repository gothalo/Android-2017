package cat.foixench.ceina.rest;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import cat.foixench.ceina.rest.network.GetAllUsersTask;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    Context context;

    @Before
    public void _setUp () {
        context = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("cat.foixench.ceina.rest", appContext.getPackageName());
    }

    @Test
    public void getClientsTest() throws Exception {
        GetAllUsersTask tsk = new GetAllUsersTask(new RecyclerView(context));
        tsk.execute();


    }
}
