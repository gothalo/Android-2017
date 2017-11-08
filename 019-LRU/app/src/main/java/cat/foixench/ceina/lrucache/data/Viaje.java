package cat.foixench.ceina.lrucache.data;

/**
 * Created by llorenc on 08/11/2017.
 */

public class Viaje {

    private String titulo;
    private int foto;

    public Viaje (String titulo, int foto) {
        this.titulo = titulo;
        this.foto = foto;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }
}
