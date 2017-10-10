package cat.foixench.listview.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import cat.foixench.listview.R;
import cat.foixench.listview.data.Elemento;

/**
 * Created by llorenc on 08/10/2017.
 */

public class CustomAdapter extends BaseAdapter {
    private Context context;
    private List<Elemento> items;

    public CustomAdapter (Context context, List<Elemento> items) {
        this.context = context;
        this.items = items;
    }

    /**
     * Devuelve el numero de elementos almacenados en el adapter
     * @return
     */
    @Override
    public int getCount() {
        return this.items.size();
    }

    /**
     * devuelve el item en la posición i del adapter
     * @param position
     * @return
     */
    @Override
    public Object getItem(int position) {
        return this.items.get(position);
    }

    /**
     * devuelve el id del elemento en la posicion indicada
     * @param posicion
     * @return
     */
    @Override
    public long getItemId(int posicion) {
        return posicion;
    }

    @Override
    public View getView(int posicion, View view, ViewGroup viewGroup) {
        View rowView = view;

        // si la vista no se ha creado antes, la creamos ahora
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.layout_list_item, viewGroup, false);
        }
        // recuperar buscamos los elementos donde mostraremos las partes del item
        ImageView ivElemento = (ImageView) rowView.findViewById(R.id.ivElemento);
        TextView tvElemento = (TextView) rowView.findViewById(R.id.tvElemento);

        // recuperamos el item a mostrar
        Elemento item = this.items.get(posicion);
        // volcamos contenido del item a los controles de la vista
        tvElemento.setText(item.getTexto());
        ivElemento.setImageResource(item.getImagen());

        return rowView;

    }

}
