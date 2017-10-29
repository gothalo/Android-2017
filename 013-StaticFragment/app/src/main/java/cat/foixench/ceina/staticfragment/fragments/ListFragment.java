package cat.foixench.ceina.staticfragment.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import cat.foixench.ceina.staticfragment.R;
import cat.foixench.ceina.staticfragment.activities.DetailActivity;
import cat.foixench.ceina.staticfragment.activities.MainActivity;

/**
 * Created by llorenc on 28/10/2017.
 */

public class ListFragment extends android.app.ListFragment {

    private String [] keys = new String [] {"Activity", "ContentProvider", "Service", "BroadcastReciver", "Fragment"};


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Método llamado tras la creación de la activity. Vamos a cargar la lista
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // asociamos a la listview de este fragment la lista de palabras claves. usaremos un item layout por defecto
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, keys);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        String item = (String) getListAdapter().getItem(position);
        String [] detail = getResources().getStringArray(R.array.diccionario_entradas);

        DetailFragment fragment = (DetailFragment) getFragmentManager().findFragmentById(R.id.detailFragment);

        if (fragment != null) {
            // si se encuentra el fragment, es que estamos usando el layout de tablet
            // informamos el detalle del fragment.
            fragment.setText(item, detail[position]);
        } else {
            // no estamos en la activity con dos fragments. procedemos a cargar la activity de detalle.
            Intent intent = new Intent(getActivity(), DetailActivity.class);

            intent.putExtra(DetailActivity.EXTRA_TITLE, item);
            intent.putExtra(DetailActivity.EXTRA_CONTENT, detail[position]);

            startActivity(intent);


        }
    }
}
