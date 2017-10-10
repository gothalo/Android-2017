package cat.foixench.listview;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class EjemploListActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ejemplo_list_activity);

        String[] datos = getResources().getStringArray(R.array.listaItems);
        // definimos el adapter. usaremos un listitem estándar de android y la matriz de datos guardada en los recursos.
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,datos);
        setListAdapter(adapter);


    }

    /**
     * Gestionamos el click en la lista
     * @param l
     * @param v
     * @param position
     * @param id
     */
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        String selectedItem = (String) getListView().getItemAtPosition(position);
        Toast.makeText(this, selectedItem, Toast.LENGTH_SHORT).show();
    }

    /**
     * añade el menú de opciones
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activities, menu);
        return super.onCreateOptionsMenu(menu   );
    }

    /**
     * ejecuta las acciones del menu
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i;
        switch (item.getItemId()) {
            case R.id.mniLista:
                i = new Intent (this, EjemploActivityConListaActivity.class);
                startActivity(i);
                break;
            case R.id.mniListaPersonalizada:
                i= new Intent (this, CustomItemActivity.class);
                startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
}
