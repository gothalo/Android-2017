package cat.foixench.ceina.lrucache.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cat.foixench.ceina.lrucache.R;
import cat.foixench.ceina.lrucache.data.Viaje;
import cat.foixench.ceina.lrucache.utils.Utilidades;

/**
 * Created by llorenc on 08/11/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter <ViajeViewHolder> {

    private List<Viaje> viajeList;
    private Context context;

    public RecyclerAdapter (List<Viaje> viajeList) {
        this.viajeList = viajeList;
    }

    @Override
    public ViajeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.cardview, parent, false);
        return new ViajeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViajeViewHolder holder, int position) {
        // informamos los campos de la cardview a través del viewholder
        holder.tvTitulo.setText(viajeList.get(position).getTitulo());

        // añadimos la imagen a partir del caché
        Bitmap imagen = Utilidades._LruCache.get(String.valueOf(viajeList.get(position).getFoto()));

        // miramos si la imagen no está en cache
        if (imagen == null) {
            // lanzamos un proceso en segundo plano para cargar la imagen en la vista y en caché
            ImageLoaderTask task = new ImageLoaderTask (context, holder.ivViaje);
            task.execute(viajeList.get(position).getFoto());
        } else {
            // la imagen está en el cache
            holder.ivViaje.setImageBitmap(imagen);
        }


    }

    @Override
    public int getItemCount() {
        // numero de elementos de la lista
        return viajeList.size();
    }

    public List<Viaje> getViajeList() {
        return viajeList;
    }

    public void setViajeList(List<Viaje> viajeList) {
        this.viajeList = viajeList;
    }

}
