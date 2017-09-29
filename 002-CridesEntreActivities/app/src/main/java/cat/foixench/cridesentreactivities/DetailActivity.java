package cat.foixench.cridesentreactivities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TextView;


/**
 * Created by llorenc on 29/09/2017.
 */

public class DetailActivity extends Activity {
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // recuperamos el nombre de recibido como parametro
        Intent intent = getIntent();

        String sNombre = intent.getStringExtra(MainActivity.EXTRA_NOMBRE);
        if (sNombre != null) {
            // recuperamos la TextView donde mostraremos el nombre
            TextView txt = findViewById(R.id.txtSaludo);
            txt.setText(getString(R.string.saludo) + " " + sNombre);
        }


    }
}
