package cat.foixench.ceina.persistencia.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import cat.foixench.ceina.persistencia.R;
import cat.foixench.ceina.persistencia.utils.AppUtils;
import cat.foixench.ceina.persistencia.utils.SharedPrefsInterface;

public class MainActivity extends AppCompatActivity implements SharedPrefsInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onResume() {
        super.onResume();

        // recuperamos el nombre del usuario de las preferencias del sistema para añadir una cabecera
        TextView txtHeader = (TextView) findViewById(R.id.txtHeader);
        txtHeader.setText(getString (R.string.hola, AppUtils.getStringPreference(this, SharedPrefsInterface.PARAM_USER_NAME)));

        // leemos los datos guardados anteriormente
        EditText editText = (EditText) findViewById(R.id.etNotas);
        editText.setText (readDataFile());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i;
        switch (item.getItemId()) {
            case R.id.mniSettings :
                i = new Intent(this, PreferencesActivity.class);
                startActivity(i);
                break;
            case R.id.mniSaveText :
                EditText editText = (EditText) findViewById(R.id.etNotas);
                saveToFile (editText.getText().toString());
                break;
        }
        return true;

    }

    private int saveToFile (String data) {
        // revisar si se ha seleccionado guardar archivo en SD o se ha seleccionado guardar archivo en memoria interna
        if (AppUtils.getBooleanPreference(this, PARAM_USE_SD)) {
            // verificación. miramos que tengamos tarjeta SD
            if (AppUtils.isSDCardEnabled() == SD_State.OK) {
                return this.saveToSDCard (data);
            } else {
                Toast.makeText(this, getString (R.string.err_no_sd), Toast.LENGTH_SHORT).show();
                return RESULT_OK;
            }
        } else {
            // guardamos en memoria interna
            return this.saveToInternalMemory(data);

        }
    }

    /**
     * guarda un fichero en la SD
     * @param data
     * @return
     */
    private int saveToSDCard (String data) {
        FileOutputStream fos = null;
        try {
            // recuperamos el archivo que esta guardado en la tarjeta externa
            File myDataFile = new File (getExternalFilesDir(APP_DATA_FOLDER), this.APP_DATA_FILE);

            // abrir fichero y guardar datos
            fos = new FileOutputStream(myDataFile);
            fos.write(data.getBytes());

        } catch (FileNotFoundException fnfe) {
            // no se encuentra el fichero.
            Log.e(APP_TAG, "File not found", fnfe);
            Toast.makeText(this, getString(R.string.err_file_not_found), Toast.LENGTH_SHORT).show();
            return RESULT_CANCELED;
        } catch (IOException ioe) {
            // error general de lectura y escritura
            Log.e(APP_TAG, "Error writting file", ioe);
            Toast.makeText(this, getString(R.string.err_io_w), Toast.LENGTH_SHORT).show();
            return RESULT_CANCELED;
        } finally {
            // se intenta cerrar el fichero si esta abierto
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException ioe) {
                Log.e(APP_TAG, "Error closing  file", ioe);
            }
        }
        return RESULT_OK;
    }

    /**
     * Guarda un texto en un archivo en la memoria interna
     * @param data
     * @return
     */
    private int saveToInternalMemory (String data) {
        FileOutputStream fos = null;
        try
        {
            // recuperamos el fichero del entorno privado de esta app
            fos = openFileOutput(this.APP_DATA_FILE, Context.MODE_PRIVATE);
            // escribimos el fichero
            fos.write(data.getBytes());
        }
        catch (FileNotFoundException fnfe) {
            Log.e(APP_TAG, "File not found", fnfe);
            Toast.makeText(this, getString(R.string.err_file_not_found), Toast.LENGTH_SHORT).show();
            return RESULT_CANCELED;
        }
        catch (IOException ioe) {
            Log.e(APP_TAG, "Error writting file", ioe);
            Toast.makeText(this, getString(R.string.err_io_w), Toast.LENGTH_SHORT).show();
            return RESULT_CANCELED;
        } finally {
            try {
                // se intenta cerrar el fichero si esta abierto
                if (fos != null) {
                    fos.close();
                }
            }
            catch (IOException ioe) {
                Log.e(APP_TAG, "Error closing  file", ioe);
            }
        }

        return RESULT_OK;
    }

    /**
     * Se encarga de discernir donde esta guardado el fichero de la aplicación
     * @return
     */
    private String readDataFile () {
        // seleccionamos el origen de los datos en función de si está en la memoria interna o en la SD
        if (AppUtils.getBooleanPreference(this, PARAM_USE_SD))  {
            // verificamos que tengamos SD
            if (AppUtils.isSDCardEnabled() == SD_State.OK) {
                return this.readFromoSDCard ();
            } else {
                Toast.makeText(this, getString (R.string.err_no_sd), Toast.LENGTH_SHORT).show();
                return "";
            }
        } else {
            // leemos desde la SD
            return this.readFromInternalMemory();
        }
    }

    /**
     * Lee el fichero de datos de la aplicación cuando este se almacena en la memoria interna del dispositivo
     * @return
     */
    private String readFromInternalMemory () {
        String dataReaded = "";
        byte[] buffer = new byte [1024];

        FileInputStream fin = null;
        try {
            fin = openFileInput(this.APP_DATA_FILE);
            StringBuilder data = new StringBuilder();
            while (fin.read(buffer) != -1) {
                data.append (new String (buffer));
            }
            dataReaded = data.toString();

        } catch (FileNotFoundException fnfe) {
            Log.e(APP_TAG, "Can't read input file. File Not Found at internal memory");
            Toast.makeText(this, getString(R.string.err_file_not_found), Toast.LENGTH_SHORT).show();

        } catch (IOException ioe) {
            Log.e(APP_TAG, "Error writting file", ioe);
            Toast.makeText(this, getString(R.string.err_io_r), Toast.LENGTH_SHORT).show();

        } finally {
            try {
                if (fin != null) {
                    fin.close();
                }
            }
            catch (IOException ioe) {
                Log.e(APP_TAG, "Error closing  file", ioe);
            }
        }
        return dataReaded;
    }

    private String readFromoSDCard() {
        String dataReaded = "";
        byte[] buffer = new byte [1024];

        FileInputStream fin = null;
        try {
            File myDataFile = new File (getExternalFilesDir(this.APP_DATA_FOLDER), this.APP_DATA_FILE);

            /*
            Para acceder a un directorio "comun", como puede ser el de fotos
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
             */

            /*
            Para acceder al ditectorio cache de la aplicación
                this.getCacheDir()
             */

            fin = new FileInputStream(myDataFile);

            StringBuilder data = new StringBuilder();
            while (fin.read(buffer) != -1) {
                data.append (new String (buffer));
            }
            dataReaded = data.toString();

        } catch (FileNotFoundException fnfe) {
            Log.e(APP_TAG, "Can't read input file. File Not Found at internal memory");
            Toast.makeText(this, getString(R.string.err_file_not_found), Toast.LENGTH_SHORT).show();

        } catch (IOException ioe) {
            Log.e(APP_TAG, "Error writting file", ioe);
            Toast.makeText(this, getString(R.string.err_io_r), Toast.LENGTH_SHORT).show();

        } finally {
            try {
                if (fin != null) {
                    fin.close();
                }
            }
            catch (IOException ioe) {
                Log.e(APP_TAG, "Error closing  file", ioe);
            }
        }
        return dataReaded;

    }

}
