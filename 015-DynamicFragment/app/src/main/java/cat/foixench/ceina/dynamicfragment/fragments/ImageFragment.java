package cat.foixench.ceina.dynamicfragment.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cat.foixench.ceina.dynamicfragment.R;

/**
 * Created by llorenc on 29/10/2017.
 */

public class ImageFragment extends Fragment {

    public static final String FRAGMENT_TAG="ImageFragment";

    /**
     * Metodo factory para la creación de fagments
     * @return
     */
    public static ImageFragment newInstance () {
        return new ImageFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        // creamos el contenido del fragment
        return inflater.inflate(R.layout.image_content_fragment, container, false);
    }
}
