package cat.foixench.ceina.staticfragment.fragments;


import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import cat.foixench.ceina.staticfragment.R;

/**
 * Created by llorenc on 28/10/2017.
 */

public class DictionaryListFragment extends ListFragment {

    private String [] keys = new String [] {"Activity", "ContentProvider", "Service", "BroadcastReciver", "Fragment"};

    // listener personalizado para que la app principal gestione los fragments
    private FragmentListener listener;


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

        if (listener != null) {
            listener.onListItemSelected(item, detail[position]);
        }
    }

    // vamos a crear un listener para escuchar un evento que generaremos cuando se seleccione un item.
    // de este modo delegaremos el trabajo de gestionar el click, no en este fragment, sino en la
    // activity que lo contiene.

    public interface FragmentListener {
        void onListItemSelected (String item, String detail);
    }
    public void setFragmentListener (FragmentListener listener) {
        this.listener = listener;
    }
}
