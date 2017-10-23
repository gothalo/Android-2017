package cat.foixench.ceina.notebook.data;

import java.util.Date;

/**
 * Representa el contenido de una nota
 */
public class Note {
    private long id;
    private String title;
    private String content;
    private Date creationDate;
    private Date updateDate;

    /**
     * constructor que inicializa todos los parámetros de la nota
     * @param id identificador de la nota
     * @param title título de la nota
     * @param content cuerpo de la nota
     * @param creationDate fecha de creación de la nota
     * @param updateDate fecha de útlima actualización de la nota
     */
    public Note (long id, String title, String content, Date creationDate, Date updateDate) {
        this.setId (id);
        this.setTitle(title);
        this.setContent(content);
        this.setCreationDate(creationDate);
        this.setUpdateDate(updateDate);
    }

    /**
     * Recupera el identificador de la nota
     * @return identificador de la nota
     */
    public long getId() {
        return id;
    }

    /**
     * asigna un identificador a esta nota
     * @param id el nuevo identificador de la nota
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Recupera el título de la nota
     * @return el título de la nota
     */
    public String getTitle() {
        return title;
    }

    /**
     * pone título a la nota
     * @param title el nuevo título de la nota
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * recupera el cuerpo de la nota
     * @return el cuerpo de la nota
     */
    public String getContent() {
        return content;
    }

    /**
     * modifica el cuerpo de la nota
     * @param content el contendio de la nota
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * fija la fecha de creación de la nota
     * @return la fecha de creación de la nota
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * obtiene la fecha de creación de la nota
     * @param creationDate valor de la fecha de creación de la nota
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * recupera la fecha de actualización de la nota
     * @return fecha de última actualización de la nota
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * guarda la fecha de última actualización de la nota
     * @param updateDate fecha de última actualización de la nota
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
