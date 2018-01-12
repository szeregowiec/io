package Admin;

/**
 * Klasa pomocnicza aby przekazać do modelu konkretny obiekt do pokazania na stronie
 */
public class Reservation {

    private String title;
    private String authors;
    private String user;
    private int idReservation;


    public Reservation(String title, String authors, String user, int idReservation) {
        this.title = title;
        this.authors = authors;
        this.user = user;
        this.idReservation = idReservation;
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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getIdReservation() {
        return idReservation;
    }

    public void setIdReservation(int idReservation) {
        this.idReservation = idReservation;
    }
}
