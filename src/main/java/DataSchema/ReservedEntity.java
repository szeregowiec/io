package DataSchema;

import javax.persistence.*;

@Entity
@Table(name = "reserved", schema = "adbuczek", catalog = "")
public class ReservedEntity {
    private String idReserved;
    private String idBook;
    private String isbn;
    private String idReader;
    private String reservedDate;
    private String expireDate;

    @Id
    @Column(name = "id_reserved", nullable = false, length = 50)
    public String getIdReserved() {
        return idReserved;
    }

    public void setIdReserved(String idReserved) {
        this.idReserved = idReserved;
    }

    @Basic
    @Column(name = "id_book", nullable = true, length = 50)
    public String getIdBook() {
        return idBook;
    }

    public void setIdBook(String idBook) {
        this.idBook = idBook;
    }

    @Basic
    @Column(name = "isbn", nullable = false, length = 50)
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
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
    @Column(name = "reserved_date", nullable = false, length = 50)
    public String getReservedDate() {
        return reservedDate;
    }

    public void setReservedDate(String reservedDate) {
        this.reservedDate = reservedDate;
    }

    @Basic
    @Column(name = "expire_date", nullable = true, length = 50)
    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReservedEntity that = (ReservedEntity) o;

        if (idReserved != null ? !idReserved.equals(that.idReserved) : that.idReserved != null) return false;
        if (idBook != null ? !idBook.equals(that.idBook) : that.idBook != null) return false;
        if (isbn != null ? !isbn.equals(that.isbn) : that.isbn != null) return false;
        if (idReader != null ? !idReader.equals(that.idReader) : that.idReader != null) return false;
        if (reservedDate != null ? !reservedDate.equals(that.reservedDate) : that.reservedDate != null) return false;
        if (expireDate != null ? !expireDate.equals(that.expireDate) : that.expireDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idReserved != null ? idReserved.hashCode() : 0;
        result = 31 * result + (idBook != null ? idBook.hashCode() : 0);
        result = 31 * result + (isbn != null ? isbn.hashCode() : 0);
        result = 31 * result + (idReader != null ? idReader.hashCode() : 0);
        result = 31 * result + (reservedDate != null ? reservedDate.hashCode() : 0);
        result = 31 * result + (expireDate != null ? expireDate.hashCode() : 0);
        return result;
    }
}
