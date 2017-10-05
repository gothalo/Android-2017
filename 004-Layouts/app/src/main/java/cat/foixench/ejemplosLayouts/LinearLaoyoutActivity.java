package cat.foixench.ejemplosLayouts;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

public class LinearLaoyoutActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linear_laoyout);

        // añadimos el boton atras en la barra de acciones
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }



}
