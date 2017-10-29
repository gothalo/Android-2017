package cat.foixench.ceina.staticfragment.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import cat.foixench.ceina.staticfragment.R;

/**
 * Created by llorenc on 28/10/2017.
 */

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_TITLE = "EXTRA_TITLE";
    public static final String EXTRA_CONTENT = "EXTRA_CONTENT";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity_layout);

        // buscamos si tenemos algun extra en esta activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String s = extras.getString(EXTRA_TITLE);
            TextView tvHeader = (TextView) findViewById(R.id.header);
            tvHeader.setText(s);
            s = extras.getString (EXTRA_CONTENT);
            TextView tvContent = (TextView) findViewById(R.id.content);
            tvContent.setText(s);
        }

    }
}
