package cat.foixench.ceina.dynamicfragment.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import cat.foixench.ceina.dynamicfragment.R;
import cat.foixench.ceina.dynamicfragment.fragments.ImageFragment;
import cat.foixench.ceina.dynamicfragment.fragments.TextFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout);

        // cargar el fragment inicial
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.frmContents, TextFragment.newInstance(), TextFragment.FRAGMENT_TAG);
        transaction.commit();



        ImageButton btnForward = (ImageButton) findViewById(R.id.btnForward);
        ImageButton btnBack = (ImageButton) findViewById(R.id.btnBack);

        btnForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();

                if (fragmentManager.findFragmentByTag(ImageFragment.FRAGMENT_TAG) == null) {
                    // no está cargado el fragment de imagen, por lo que lo cambiamos
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    // transaction.setCustomAnimations(R.animator.slide_left_in, R.animator.slide_left_out);
                    // transaction.addToBackStack(null);
                    transaction.replace(R.id.frmContents, ImageFragment.newInstance(), ImageFragment.FRAGMENT_TAG);
                    transaction.commit();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                // verificamos que no tengamos cargdo el fragment de texto usando su tag
                if (fragmentManager.findFragmentByTag(TextFragment.FRAGMENT_TAG) == null) {
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    // transaction.setCustomAnimations(R.animator.appear, R.animator.disappear);
                    transaction.replace(R.id.frmContents, TextFragment.newInstance(), TextFragment.FRAGMENT_TAG);
                    transaction.commit();
                }
            }
        });
    }
}
