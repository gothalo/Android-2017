package cat.foixench.ejemplosLayouts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity);
    }

    public void onGridLayout (View view) {
        Intent i = new Intent(this, GridLayoutActivity.class);
        startActivity(i);
    }

    public void onFrameLayout (View view) {
        Intent i = new Intent(this, FrameLayoutActivity.class);
        startActivity(i);
    }

    public void onTableLayout (View view) {
        Intent i = new Intent(this, TableLayoutActivity.class);
        startActivity(i);
    }

    public void onRelativeLayout (View view) {
        Intent i = new Intent(this, RelativeLayoutActivity.class);
        startActivity(i);
    }

    public void onLinearLayout (View view) {
        Intent i = new Intent(this, LinearLaoyoutActivity.class);
        startActivity(i);
    }


}
