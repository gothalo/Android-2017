package cat.foixench.ceina.sqlite.data;

/**
 * Clase que representa una tienda
 */

public class Shop {

    private long id;
    private String name;
    private String address;

    /**
     * Constructor que inicializa los tres parámetros de la base de datos.
     */
    public Shop (long id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }



}
