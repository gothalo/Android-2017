package cat.foixench.ceina.pestannas.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cat.foixench.ceina.pestannas.R;

/**
 * Created by llorenc on 14/10/2017.
 */

public class TabHomeFragment extends Fragment {
    /**
     * Factory para la creación de nuevas instancias de la clase
     * @return
     */
    public static TabHomeFragment newInstance() {
        return new TabHomeFragment();
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment_layout, container, false);
    }
}
