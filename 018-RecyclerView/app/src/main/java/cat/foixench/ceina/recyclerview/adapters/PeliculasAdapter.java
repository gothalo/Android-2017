package cat.foixench.ceina.recyclerview.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cat.foixench.ceina.recyclerview.R;
import cat.foixench.ceina.recyclerview.data.Pelicula;

/**
 * Created by llorenc on 02/11/2017.
 */

public class PeliculasAdapter extends RecyclerView.Adapter <PeliculasAdapter.PeliculasViewHolder>{

    private List<Pelicula> peliculasList;

    /**
     * Clase interna ViewHolder que se encarga de asociar la información del item de manera menos costosa.
     */
    public class PeliculasViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitulo, tvAnno, tvGenero;

        public PeliculasViewHolder (View view) {
            super (view);
            tvTitulo = (TextView) view.findViewById(R.id.title);
            tvGenero = (TextView) view.findViewById(R.id.genre);
            tvAnno = (TextView) view.findViewById(R.id.year);
        }
    }

    public PeliculasAdapter(List<Pelicula> peliculasList) {
        this.peliculasList = peliculasList;
    }

    @Override
    public PeliculasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.pelicula_list_item, parent, false);
        return new PeliculasViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PeliculasViewHolder holder, int position) {
        Pelicula pelicula = peliculasList.get(position);
        holder.tvTitulo.setText(pelicula.getTitulo());
        holder.tvAnno.setText(pelicula.getAnno());
        holder.tvGenero.setText(pelicula.getGenero());
    }

    @Override
    public int getItemCount() {
        return peliculasList.size();
    }
}
