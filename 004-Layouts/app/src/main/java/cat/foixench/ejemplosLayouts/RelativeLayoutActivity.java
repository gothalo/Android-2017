package cat.foixench.ejemplosLayouts;

import android.os.Bundle;
import android.app.Activity;

public class RelativeLayoutActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relative_layout);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
