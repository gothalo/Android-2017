package cat.foixench.ciclovida;


import android.os.Bundle;
import android.app.Activity;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText (this, "onCreate", Toast.LENGTH_SHORT).show ();

    }

    @Override
    protected void onStart() {
        super.onStart();

        Toast.makeText (this, "onStart", Toast.LENGTH_SHORT).show ();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Toast.makeText (this, "onRestart", Toast.LENGTH_SHORT).show ();
    }

    @Override
    protected void onResume () {
        super.onResume ();

        Toast.makeText (this, "onResume", Toast.LENGTH_SHORT).show ();
    }

    @Override
    protected void onPause() {
        super.onPause();

        Toast.makeText (this, "onPause", Toast.LENGTH_SHORT).show ();
    }

    @Override
    protected void onStop() {
        super.onStop();

        Toast.makeText (this, "onStop", Toast.LENGTH_SHORT).show ();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Toast.makeText (this, "onDestroy", Toast.LENGTH_SHORT).show ();
    }
}
