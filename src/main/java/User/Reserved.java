package User;

import java.sql.Date;


/**
 * Klasa pomocnicza do wyświetlania odpowidniego obiektu w velocity
 */
public class Reserved {
    private String title;
    private String authors;
    private Date reservedDate;
    private Date expireDate;
    private int idReserved;

    public Reserved(String title, String authors, Date reservedDate, Date expireDate, int idReserved) {
        this.title = title;
        this.authors = authors;
        this.reservedDate = reservedDate;
        this.expireDate = expireDate;
        this.idReserved = idReserved;
    }

    public int getIdReserved() {
        return idReserved;
    }

    public void setIdReserved(int idReserved) {
        this.idReserved = idReserved;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public Date getReservedDate() {
        return reservedDate;
    }

    public void setReservedDate(Date reservedDate) {
        this.reservedDate = reservedDate;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }


}
