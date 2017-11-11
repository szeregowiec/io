package DataSchema;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "borrowed", schema = "adbuczek", catalog = "")
public class BorrowedEntity {
    private String idBorrowed;
    private String idBook;
    private String idReader;
    private Date borrowedDate;
    private Date returnDate;

    @Id
    @Column(name = "id_borrowed", nullable = false, length = 50)
    public String getIdBorrowed() {
        return idBorrowed;
    }

    public void setIdBorrowed(String idBorrowed) {
        this.idBorrowed = idBorrowed;
    }

    @Basic
    @Column(name = "id_book", nullable = false, length = 50)
    public String getIdBook() {
        return idBook;
    }

    public void setIdBook(String idBook) {
        this.idBook = idBook;
    }

    @Basic
    @Column(name = "id_reader", nullable = false, length = 50)
    public String getIdReader() {
        return idReader;
    }

    public void setIdReader(String idReader) {
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

        if (idBorrowed != null ? !idBorrowed.equals(that.idBorrowed) : that.idBorrowed != null) return false;
        if (idBook != null ? !idBook.equals(that.idBook) : that.idBook != null) return false;
        if (idReader != null ? !idReader.equals(that.idReader) : that.idReader != null) return false;
        if (borrowedDate != null ? !borrowedDate.equals(that.borrowedDate) : that.borrowedDate != null) return false;
        if (returnDate != null ? !returnDate.equals(that.returnDate) : that.returnDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idBorrowed != null ? idBorrowed.hashCode() : 0;
        result = 31 * result + (idBook != null ? idBook.hashCode() : 0);
        result = 31 * result + (idReader != null ? idReader.hashCode() : 0);
        result = 31 * result + (borrowedDate != null ? borrowedDate.hashCode() : 0);
        result = 31 * result + (returnDate != null ? returnDate.hashCode() : 0);
        return result;
    }
}
