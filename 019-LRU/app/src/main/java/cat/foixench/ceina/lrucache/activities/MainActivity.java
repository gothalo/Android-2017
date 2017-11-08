package cat.foixench.ceina.lrucache.activities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.LruCache;

import cat.foixench.ceina.lrucache.R;
import cat.foixench.ceina.lrucache.adapter.RecyclerAdapter;
import cat.foixench.ceina.lrucache.utils.Utilidades;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // configuramos el cache de almacenamiento de imagenes
        final int memoriaDisponible = (int) (Runtime.getRuntime().maxMemory() / 1024);

        Utilidades._LruCache = new LruCache<String, Bitmap>(memoriaDisponible / 8) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                //Por defecto, el tamaño de la cache se mide en elementos. queremos usar kb para evitar
                // que se vaya de madre.
                return value.getByteCount() / 1024;
            }
        };

        // configurar la recyclerView
        RecyclerView recyclerView =  (RecyclerView) findViewById(R.id.rvViajes);

        // layout manager
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        // asignamos el adapter
        RecyclerAdapter adapter = new RecyclerAdapter (Utilidades.initializeData());
        recyclerView.setAdapter(adapter);






    }

}
