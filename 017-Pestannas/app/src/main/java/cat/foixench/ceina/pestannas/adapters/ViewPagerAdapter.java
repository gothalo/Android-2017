package cat.foixench.ceina.pestannas.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by llorenc on 14/10/2017.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    /**
     * Constructor de la clase. Recibe un fragment manager que ha de permitir la gestion de las pestañas.
     * @param manager
     */
    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    // recupera el fragment guardado en la posicion n
    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    // recupera el numero total de pestañas
    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    // recupera el titulo de las pestañas.
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    // añade un nuevo fragment en el entrono
    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }
}
