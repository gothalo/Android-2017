package cat.foixench.cridesintent;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.util.Hashtable;

public class MainActivity extends Activity implements View.OnClickListener {

    private static final int CAMERA_REQUEST = 1888;
    private static final String PHOTO_INTENT = "photo_intent";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Asignamos evento click a los botones.

        Button btnBrowser = findViewById(R.id.btnBrowser);
        btnBrowser.setOnClickListener(this);

        Button btnTexto = findViewById(R.id.btnTexto);
        btnTexto.setOnClickListener(this);

        Button btnLlamada =  findViewById(R.id.btnLLamada);
        btnLlamada.setOnClickListener(this);

        Button btnEmail = findViewById(R.id.btnEmail);
        btnEmail.setOnClickListener(this);

        Button btnFoto = findViewById(R.id.btnFoto);
        btnFoto.setOnClickListener(this);
    }

    /**
     * Gestiona el evento en los botones click
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
                intent.setData (Uri.parse("mailto:ejemplo@ejemplo.com"));
                intent.putExtra(Intent.EXTRA_SUBJECT, "asunto de prueba");
                intent.putExtra(Intent.EXTRA_TEXT, "probando el envio");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] {"sheldon@cooper.com"});
                startActivity(intent);
                break;
            case R.id.btnFoto:
                getPicture ();
                break;
        }
    }

    /**
     * Abre un intent implicito
     * @param action indica la acció a realitzar
     * @param uri Uri del recurs cridat
     */
    private void openIntent(String action, String uri)
    {
        Intent intent = new Intent(action,  Uri.parse(uri));
        startActivity (intent);
    }

    /**
     * Abre un intent implicito, pasandole parámetros
     * @param action indica la acció a realitzar
     * @param type mime/type del contingut
     * @param params extres enviats al Intent
     */
    private void openIntent (String action, String type, Hashtable <String, String> params) {
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
     * @param requestCode codi indicat per la app per fer la crida
     * @param resultCode flag de resultat de la crida
     * @param data intent amb el resultat de la crida
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ImageView imageView = findViewById(R.id.imgPhoto);

        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
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
}
