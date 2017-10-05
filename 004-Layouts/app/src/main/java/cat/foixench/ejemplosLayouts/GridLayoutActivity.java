package cat.foixench.ejemplosLayouts;

import android.os.Bundle;
import android.app.Activity;

public class GridLayoutActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_layout);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
