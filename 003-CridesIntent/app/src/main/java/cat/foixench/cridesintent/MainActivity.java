package cat.foixench.cridesintent;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;

public class MainActivity extends Activity implements View.OnClickListener {

    private static final int CAMERA_REQUEST = 1888;
    private static final String PHOTO_INTENT = "photo_intent";
    private static final int MY_PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE = 1;

    private Bitmap photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Asignamos evento click a los botones.

        Button btnBrowser = findViewById(R.id.btnBrowser);
        btnBrowser.setOnClickListener(this);

        Button btnTexto = findViewById(R.id.btnTexto);
        btnTexto.setOnClickListener(this);

        Button btnLlamada = findViewById(R.id.btnLLamada);
        btnLlamada.setOnClickListener(this);

        Button btnEmail = findViewById(R.id.btnEmail);
        btnEmail.setOnClickListener(this);

        Button btnFoto = findViewById(R.id.btnFoto);
        btnFoto.setOnClickListener(this);
    }

    /**
     * Gestiona el evento en los botones click
     *
     * @param view vista que origina l'event
     */
    @Override
    public void onClick(View view) {
        Hashtable<String, String> params;
        // procesamos el evento en función del origen del evento.
        switch (view.getId()) {
            case R.id.btnBrowser:
                openIntent(Intent.ACTION_VIEW, "http://www.google.com");
                break;
            case R.id.btnTexto:
                params = new Hashtable<>();
                params.put(Intent.EXTRA_TEXT, getString(R.string.textoEjemplo));
                openIntent(Intent.ACTION_SEND, "text/plain", params);
                break;
            case R.id.btnLLamada:
                openIntent(Intent.ACTION_DIAL, "tel:5551234");
                break;
            case R.id.btnEmail:
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setType("text/plain");
                intent.setData(Uri.parse("mailto:ejemplo@ejemplo.com"));
                intent.putExtra(Intent.EXTRA_SUBJECT, "asunto de prueba");
                intent.putExtra(Intent.EXTRA_TEXT, "probando el envio");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"sheldon@cooper.com"});
                startActivity(intent);
                break;
            case R.id.btnFoto:
                getPicture();
                break;
        }
    }

    /**
     * Abre un intent implicito
     *
     * @param action indica la acció a realitzar
     * @param uri    Uri del recurs cridat
     */
    private void openIntent(String action, String uri) {
        Intent intent = new Intent(action, Uri.parse(uri));
        startActivity(intent);
    }

    /**
     * Abre un intent implicito, pasandole parámetros
     *
     * @param action indica la acció a realitzar
     * @param type   mime/type del contingut
     * @param params extres enviats al Intent
     */
    private void openIntent(String action, String type, Hashtable<String, String> params) {
        Intent intent = new Intent(action);
        intent.setType(type);
        if (params != null) {
            for (String key : params.keySet()) {
                intent.putExtra(key, params.get(key));
            }
        }
        startActivity(intent);
    }

    /**
     * Crida a la camera per recuperar una foto
     */
    private void getPicture() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    /**
     * Aquesta activitat espera a que una altra acabi la feina, aqui es procesa el retorn
     *
     * @param requestCode codi indicat per la app per fer la crida
     * @param resultCode  flag de resultat de la crida
     * @param data        intent amb el resultat de la crida
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ImageView imageView = findViewById(R.id.imgPhoto);

        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);

            // save image to storage
            if (ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                saveImage(photo);
            } else {
                Log.e("APPTEST", "NO HAY PERMISO");
                if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                } else {
                    this.photo = photo;
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            MY_PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE);
                }
            }
        }
    }

    /**
     * desa el estat de la activity. util per guardar el estat dels controls al girar el telèfon
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ImageView imageView = findViewById(R.id.imgPhoto);
        if (imageView.getDrawable() != null) {
            Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 50, bs);

            outState.putByteArray(PHOTO_INTENT, bs.toByteArray());
        }
    }

    /**
     * recupera el estat de la activity
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState.getByteArray(PHOTO_INTENT) != null) {
            ImageView imageView = findViewById(R.id.imgPhoto);
            Bitmap bitmap = BitmapFactory.decodeByteArray(savedInstanceState.getByteArray(PHOTO_INTENT)
                    , 0
                    , savedInstanceState.getByteArray(PHOTO_INTENT).length);
            imageView.setImageBitmap(bitmap);
        }
    }

    private void saveImage(Bitmap photo) {
        String imgName = "img_" + Long.toString(System.currentTimeMillis()) + ".jpg";

        File root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        File imgFile = new File(root, imgName);

        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(imgFile);
            // enviamos los datos a la tarjeta sd comprimidos como jpg
            photo.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();

        } catch (FileNotFoundException fnfe) {
            Log.e("APPTEST", "File Not Found", fnfe);
        } catch (IOException ioe) {
            Log.e("APPTEST", "IO Exception", ioe);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException ioe) {
                    Log.e("APPTEST", "IO Exception", ioe);
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,  int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE :
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (this.photo != null) {
                        saveImage(this.photo);
                    }
                }

        }

    }
}

