package cat.foixench.ceina.recyclerview.data;

/**
 * Created by llorenc on 01/11/2017.
 */

public class Pelicula {

    private String titulo, genero, anno;

    public Pelicula() {

    }

    public Pelicula(String titulo, String genero, String anno) {
        this.titulo = titulo;
        this.genero = genero;
        this.anno = anno;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getAnno() {
        return anno;
    }

    public void setAnno(String anno) {
        this.anno = anno;
    }



}
