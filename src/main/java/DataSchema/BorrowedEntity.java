package DataSchema;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "borrowed", schema = "adbuczek", catalog = "")
public class BorrowedEntity {
    private int idBorrowed;
    private int idBook;
    private int idReader;
    private Date borrowedDate;
    private Date returnDate;

    @Id
    @Column(name = "id_borrowed", nullable = false, length = 50)
    public int getIdBorrowed() {
        return idBorrowed;
    }

    public void setIdBorrowed(int idBorrowed) {
        this.idBorrowed = idBorrowed;
    }

    @Basic
    @Column(name = "id_book", nullable = false, length = 50)
    public int getIdBook() {
        return idBook;
    }

    public void setIdBook(int idBook) {
        this.idBook = idBook;
    }

    @Basic
    @Column(name = "id_reader", nullable = false, length = 50)
    public int getIdReader() {
        return idReader;
    }

    public void setIdReader(int idReader) {
        this.idReader = idReader;
    }

    @Basic
    @Column(name = "borrowed_date", nullable = false)
    public Date getBorrowedDate() {
        return borrowedDate;
    }

    public void setBorrowedDate(Date borrowedDate) {
        this.borrowedDate = borrowedDate;
    }

    @Basic
    @Column(name = "return_date", nullable = false)
    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BorrowedEntity that = (BorrowedEntity) o;

        if (idBorrowed != that.idBorrowed) return false;
        if (idBook != that.idBook) return false;
        if (idReader != that.idReader) return false;
        if (borrowedDate != null ? !borrowedDate.equals(that.borrowedDate) : that.borrowedDate != null) return false;
        if (returnDate != null ? !returnDate.equals(that.returnDate) : that.returnDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idBorrowed;
        result = result + idBook;
        result = result + idReader;
        result = 31 * result + (borrowedDate != null ? borrowedDate.hashCode() : 0);
        result = 31 * result + (returnDate != null ? returnDate.hashCode() : 0);
        return result;
    }
}
