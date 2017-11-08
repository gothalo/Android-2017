package cat.foixench.ceina.lrucache.utils;

import android.graphics.Bitmap;
import android.util.LruCache;

import java.util.ArrayList;
import java.util.List;

import cat.foixench.ceina.lrucache.data.Viaje;
import cat.foixench.ceina.lrucache.R;

/**
 * Created by llorenc on 08/11/2017.
 */

public class Utilidades {

    //Cache que permite almacenar en memoria Bitmaps, para que sean accesibles rápidamente.
    public static LruCache<String, Bitmap> _LruCache;


    //Inicializar a viajes por defecto
    public static List<Viaje> initializeData() {

        List<Viaje> viajes = new ArrayList<>();

        viajes.add(new Viaje("Atardecer Alhambra", R.drawable.viaje_granada));
        viajes.add(new Viaje("Canal Amsterdam", R.drawable.viaje_amsterdam1));
        viajes.add(new Viaje("Playa Cerdeña", R.drawable.viaje_cerdena));
        viajes.add(new Viaje("Canal Brujas", R.drawable.viaje_brujas1));
        viajes.add(new Viaje("Notre Dame", R.drawable.viaje_paris2));
        viajes.add(new Viaje("Cerdeña", R.drawable.viaje_cerdena2));
        viajes.add(new Viaje("Gante Aérea", R.drawable.viaje_gante1));
        viajes.add(new Viaje("Lago Neuch", R.drawable.viaje_neuch));
        viajes.add(new Viaje("Torre Eiffel", R.drawable.viaje_paris));
        viajes.add(new Viaje("Santorini", R.drawable.viaje_santorini));
        viajes.add(new Viaje("Castillo Neuch", R.drawable.viaje_neuch2));
        viajes.add(new Viaje("Vista Santorini", R.drawable.viaje_santorini2));
        viajes.add(new Viaje("Puente Londres", R.drawable.viaje_londres));
        viajes.add(new Viaje("Granada", R.drawable.viaje_granada3));
        viajes.add(new Viaje("Barcelona", R.drawable.viaje_barcelona));
        viajes.add(new Viaje("Mallorca", R.drawable.viaje_mallorca));
        viajes.add(new Viaje("Columnas Granada", R.drawable.viaje_granada4));

        return  viajes;
    }

}
