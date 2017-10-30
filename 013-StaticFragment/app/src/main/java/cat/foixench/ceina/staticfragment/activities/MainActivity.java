package cat.foixench.ceina.staticfragment.activities;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import cat.foixench.ceina.staticfragment.R;
import cat.foixench.ceina.staticfragment.fragments.DetailFragment;
import cat.foixench.ceina.staticfragment.fragments.DictionaryListFragment;

/**
 * Created by llorenc on 28/10/2017.
 */

public class MainActivity extends AppCompatActivity implements DictionaryListFragment.FragmentListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout);

        DictionaryListFragment dictionaryListFragment = (DictionaryListFragment) getFragmentManager().findFragmentById(R.id.listFragment);
        if (dictionaryListFragment != null) {
            dictionaryListFragment.setFragmentListener(this);
        }
    }

    @Override
    public void onListItemSelected(String item, String detail) {
        DetailFragment fragment = (DetailFragment) getFragmentManager().findFragmentById(R.id.detailFragment);

        if (fragment != null) {
            // si se encuentra el fragment, es que estamos usando el layout de tablet
            // informamos el detalle del fragment.
            fragment.setText(item, detail);
        } else {
            // no estamos en la activity con dos fragments. procedemos a cargar la activity de detalle.
            Intent intent = new Intent(this, DetailActivity.class);

            intent.putExtra(DetailActivity.EXTRA_TITLE, item);
            intent.putExtra(DetailActivity.EXTRA_CONTENT, detail);

            startActivity(intent);
        }
    }
}
