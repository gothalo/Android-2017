package cat.foixench.ceina.tareaasincrona;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    final static int MAX_ITERATIONS = 10;

    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // asignamos acciones a los botones
        Button btnBlock = (Button) findViewById(R.id.btnBlock);
        Button btnAction = (Button) findViewById(R.id.btnAction);
        Button btnAsync = (Button) findViewById(R.id.btnAsync);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        btnBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Thread.sleep(10000L);
                } catch (InterruptedException e) {
                    Log.e("APPTEST", e.getMessage());
                }
            }
        });

        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, getString(R.string.texto_accion), Toast.LENGTH_SHORT).show();
            }
        });

        btnAsync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // lanzamos el proceso en segundo plano
                progressBar.setMax(MAX_ITERATIONS);
                new AsyncTaskExample().execute(MAX_ITERATIONS);
            }
        });
    }

    /**
     * Genera una tarea en segundo plano.
     * El primer tipo indica el tipo de parametro que recibe la tarea.
     * El segundo tipo indica el tipo de parametro que se usa en el progreso de la tarea
     * El tercer tipo indica el tipo devuelto en el resultado de la tarea
     */
    public class AsyncTaskExample extends AsyncTask<Integer, Integer, String> {

        /**
         * Pasos previos a la ejecución de la tarea
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);

        }

        /**
         * Ejecuta la tarea
         * @param integers
         * @return
         */
        @Override
        protected String doInBackground(Integer... integers) {
            for (int count = 0; count < integers [0]; count++) {
                try {
                    publishProgress(count);
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {
                    Log.e("APPTEST", "exception en background task", ie);
                }
            }

            return getString(R.string.completado);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setProgress(values[0]);

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
        }

    }


}
