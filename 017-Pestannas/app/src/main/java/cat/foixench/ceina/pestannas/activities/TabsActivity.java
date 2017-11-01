package cat.foixench.ceina.pestannas.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.TabLayout;


import cat.foixench.ceina.pestannas.R;
import cat.foixench.ceina.pestannas.adapters.ViewPagerAdapter;
import cat.foixench.ceina.pestannas.fragments.TabDashboardFragment;
import cat.foixench.ceina.pestannas.fragments.TabHomeFragment;
import cat.foixench.ceina.pestannas.fragments.TabNotificationFragment;

/**
 * Created by llorenc on 01/11/2017.
 */

public class TabsActivity extends AppCompatActivity {

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView (R.layout.activity_tabs);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager (viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons(tabLayout);
    }

    // inicializa el viewPager.
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(TabHomeFragment.newInstance(), getString(R.string.title_home));
        adapter.addFragment(TabDashboardFragment.newInstance(), getString(R.string.title_dashboard));
        adapter.addFragment(TabNotificationFragment.newInstance(), getString(R.string.title_notifications));
        
        viewPager.setAdapter(adapter);
    }

    private void setupTabIcons(TabLayout tabLayout) {
        //tabLayout.getTabAt(0).setIcon(R.drawable.ic_home_black_24dp);
    }
}