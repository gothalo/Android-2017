package cat.foixench.listview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class EjemploActivityConListaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejemplo_con_lista);

        // configuración del botón atras en la toolbar.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // configuramos la listview
        ListView listView = (ListView) findViewById(R.id.lstListaSimple);
        String [] datos = getResources().getStringArray(R.array.listaItems);
        // creamos el adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,datos);
        // asignamos el adaper a la lista
        listView.setAdapter(adapter);

        // evento click en los items
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String selectedItem = (String) adapterView.getItemAtPosition (position);
                Toast.makeText(EjemploActivityConListaActivity.this, selectedItem, Toast.LENGTH_SHORT).show();
            }
        });
    }



}
