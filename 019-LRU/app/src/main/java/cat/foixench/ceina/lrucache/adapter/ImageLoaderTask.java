package cat.foixench.ceina.lrucache.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import cat.foixench.ceina.lrucache.utils.Utilidades;

/**
 * Created by llorenc on 08/11/2017.
 */

public class ImageLoaderTask extends AsyncTask<Integer, Void, Bitmap> {
    private Context mContext;
    private ImageView img;

    public ImageLoaderTask(Context context, ImageView imageView) {
        this.mContext = context;
        this.img = imageView;
    }

    @Override
    protected Bitmap doInBackground(Integer... params) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        //inSampleSize-->Permite obtener una imagen mas pequeña a la original,trabaja con potencias de 2,
        //por lo que si ponemos 2, queremos una imagen 1/2 de la original
        options.inSampleSize =2;
        //Obtenemos el bitmap con las opciones de que sea mas pequeño
        final Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), params[0], options);
        //Añadimos el bitmap a la memoria cache
        String clave = String.valueOf(params[0]);

        //Si no existe el recurso Id en la memoria cache, buscandolo por la clave, añadimos el bitmap a la memoria
        if (Utilidades._LruCache.get(clave) == null)
            Utilidades._LruCache.put(clave, bitmap);

        return bitmap;
    }
    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);
        if(result!=null) {
            img.setImageBitmap(result);
        }
    }
}
