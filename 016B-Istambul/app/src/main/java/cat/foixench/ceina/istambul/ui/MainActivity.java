package cat.foixench.ceina.istambul.ui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import cat.foixench.ceina.istambul.R;
import cat.foixench.ceina.istambul.listeners.OnSwipeTouchListener;

public class MainActivity extends AppCompatActivity {

    private int currentFragment;
    private final static int DIRECTION_LEFT = 0;
    private final static int DIRECTION_RIGHT = 1;

    public final static String APP_TAG = "ISTAMBUL_APP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transiction = fragmentManager.beginTransaction();
        transiction.add(R.id.fragmentContainer, ContentFragment.newInstance());
        transiction.commit();

        currentFragment = 0;

        FrameLayout fragmentContainer = (FrameLayout) findViewById(R.id.fragmentContainer);

        fragmentContainer.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {
            @Override
            public void onSwipeLeft() {
                Log.d (APP_TAG, "Swipe left");
                changeContent(DIRECTION_LEFT);
            }

            @Override
            public void onSwipeRight() {
                Log.d(APP_TAG, "Swipe right");
                changeContent(DIRECTION_RIGHT);
            }
        });

        // mostramos una snackbar de información al usuario
        Snackbar.make(coordinatorLayout, R.string.app_info,Snackbar.LENGTH_LONG)
                .setAction(android.R.string.ok, null)
                .show();
    }

    public void changeContent (int direction) {
        boolean change = false;

        if (currentFragment > 0 && direction == DIRECTION_RIGHT) {
            currentFragment--;
            change = true;
        }
        if (currentFragment < 3 && direction == DIRECTION_LEFT) {
            currentFragment++;
            change = true;
        }
        if (change) {

            ContentFragment newFragment = ContentFragment.newInstance();
            newFragment.setContentCode(currentFragment);

            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            if (direction == DIRECTION_LEFT) {
                transaction.setCustomAnimations(R.animator.slide_left_in, R.animator.slide_left_out);
            } else {
                transaction.setCustomAnimations(R.animator.slide_right_in, R.animator.slide_right_out);
            }

            transaction.replace(R.id.fragmentContainer,  newFragment);
            transaction.commit();
        }

    }
}
