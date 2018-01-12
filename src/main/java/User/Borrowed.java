package User;

import java.sql.Date;

/**
 * Klasa pomocnicza do wyświetlania odpowidniego obiektu w velocity
 */
public class Borrowed {
    private String title;
    private String authors;
    private Date borrowedDate;
    private Date returnDate;
    private int idBorrowed;

    public Borrowed(String title, String authors, Date borrowedDate, Date returnDate, int idBorrowed) {
        this.title = title;
        this.authors = authors;
        this.borrowedDate = borrowedDate;
        this.returnDate = returnDate;
        this.idBorrowed = idBorrowed;
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

    public Date getBorrowedDate() {
        return borrowedDate;
    }

    public void setBorrowedDate(Date borrowedDate) {
        this.borrowedDate = borrowedDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void getReturnDate(Date expireDate) {
        this.returnDate = expireDate;
    }

    public int getIdBorrowed() {
        return idBorrowed;
    }

    public void setIdBorrowed(int idBorrowed) {
        this.idBorrowed = idBorrowed;
    }
}
