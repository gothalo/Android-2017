package cat.foixench.recursos;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // añadimos el gestor de eventos al boton
        Button btnOk = (Button) findViewById(R.id.btnOk);
        btnOk.setOnClickListener(this);

        final Button btnMnu = (Button) findViewById(R.id.menuButton);
        btnMnu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, btnMnu);

                popupMenu.getMenuInflater().inflate(R.menu.main_activity_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        return false;
                    }
                });

                popupMenu.show();
            }
        });


    }

    @Override
    public void onClick (View view) {
        String strNumeroGatitos;
        int intNumeroGatitos = -1;
        // objeto recursos para el acceso a los mismos
        Resources res = this.getResources();

        // recuperar la vista con la respuesta a la pregunta
        EditText txtGatitos  = (EditText) findViewById(R.id.preguntaGatitos);
        // recuperar la respuesta a la pregunta (como texto)
        strNumeroGatitos = txtGatitos.getText().toString();

        try {
            // convertir el valor introducido en el Edit Text a numerico
            intNumeroGatitos = Integer.parseInt(strNumeroGatitos);
        }
        catch (NumberFormatException ex) {
            // controlamos el error en caso que no se haya introducido un numero
            Toast.makeText(this, R.string.error_numerico, Toast.LENGTH_LONG).show();
            intNumeroGatitos = -1;
        }

        // mostrar un mensaje en función del número de gatitos
        if (intNumeroGatitos != -1) {
            // recuepramos la vista respuesta
            TextView txtRespuesta = (TextView) findViewById(R.id.respuestaGatitos);
            // el segundo parámetro sirve para seleccional que forma se ha de usar.
            // el tercer parámetro es opcional. Si se ha indicado formato en la cadena un formato debemos indicar aquí
            // los parámetros requeridos.
            txtRespuesta.setText(res.getQuantityString(R.plurals.gatitos, intNumeroGatitos, intNumeroGatitos));

        }
    }

    /**
     * método para inflar el menú. es decir, crea el menú para esta activitt
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // este objeto se encargará de inflar (crear) el menú
        MenuInflater inflater = getMenuInflater();
        // asociamso el menú para esta activity
        inflater.inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    /**
     * Procesa el click sobre uno de los elementos seleccionados
     * @param item representa el item de menu seleccionado
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String text="";
        // decidir acción en función del item seleccionado. lo idientificamos por su Id
        switch (item.getItemId()) {
            case R.id.mniCopy:
                text=getString(android.R.string.copy);
                break;
            case R.id.mniPaste:
                text=getString(android.R.string.paste);
                break;
            case R.id.mniSave:
                text=getString(R.string.save);
                break;
            case R.id.mniShare:
                text=getString(R.string.share);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        return true;

    }
}
