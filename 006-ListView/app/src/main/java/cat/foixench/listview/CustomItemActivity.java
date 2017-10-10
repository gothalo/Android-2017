package cat.foixench.listview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cat.foixench.listview.adapter.CustomAdapter;
import cat.foixench.listview.data.Elemento;


public class CustomItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_item);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // recuperamos la listview
        ListView lv = (ListView) findViewById(R.id.customItemsListView);

        // creamos unos elementos de pruebas
        List elementos = new ArrayList<Elemento>();

        elementos.add(new Elemento(R.mipmap.ic_android, "Androide"));
        elementos.add(new Elemento(R.mipmap.ic_baby, "Baby"));
        elementos.add(new Elemento(R.mipmap.ic_film, "Film"));

        // creamos el adapter y lo asignamos a la lista

        lv.setAdapter(new CustomAdapter(this, elementos));

    }

}
