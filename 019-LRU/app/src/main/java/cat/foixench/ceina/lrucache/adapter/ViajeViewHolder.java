package cat.foixench.ceina.lrucache.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cat.foixench.ceina.lrucache.R;

/**
 * Created by llorenc on 08/11/2017.
 */

public class ViajeViewHolder extends RecyclerView.ViewHolder {

    CardView cardView;
    ImageView ivViaje;
    TextView tvTitulo;

    public ViajeViewHolder(View itemView) {
        super(itemView);

        cardView = itemView.findViewById(R.id.cardViewTrip);
        ivViaje = itemView.findViewById(R.id.imageTrip);
        tvTitulo = itemView.findViewById(R.id.titleTrip);
    }
}
