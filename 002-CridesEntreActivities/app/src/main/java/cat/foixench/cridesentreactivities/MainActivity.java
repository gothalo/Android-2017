package cat.foixench.cridesentreactivities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements View.OnClickListener {
    public static final String EXTRA_NOMBRE = "EXTRA_NOMBRE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // añadir un gestor de eventos al botón
        Button btn = findViewById(R.id.btnEnviar);
        btn.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {

        // recuperar el texto indicado en la ventana inicial
        EditText txtNombre = findViewById(R.id.txtName);
        if (txtNombre != null) {
            String sNombre = txtNombre.getText().toString();
            // lamar a la actividad pasando el nombre del usuario
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra(this.EXTRA_NOMBRE, sNombre);
            startActivity(intent);
        }

    }
}
