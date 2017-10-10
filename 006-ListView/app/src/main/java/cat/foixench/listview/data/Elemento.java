package cat.foixench.listview.data;

/**
 * Created by llorenc on 08/10/2017.
 */

public class Elemento {
    private int imagen;
    private String texto;

    public Elemento (int imagen, String texto) {
        this.imagen = imagen;
        this.texto = texto;
    }

    public int getImagen () {
        return imagen;
    }
    public String getTexto() {
        return this.texto;
    }

}
