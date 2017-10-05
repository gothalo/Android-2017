package cat.foixench.ejemplosLayouts;

import android.os.Bundle;
import android.app.Activity;

public class TableLayoutActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_layout);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
