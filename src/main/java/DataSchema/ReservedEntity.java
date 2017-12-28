package DataSchema;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "reserved", schema = "adbuczek", catalog = "")
public class ReservedEntity {
    private int idReserved;
    private String isbn;
    private int idReader;
    private Date reservedDate;
    private Date expireDate;

    @Id
    @Column(name = "id_reserved", nullable = false)
    public int getIdReserved() {
        return idReserved;
    }

    public void setIdReserved(int idReserved) {
        this.idReserved = idReserved;
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
    @Column(name = "id_reader", nullable = false)
    public int getIdReader() {
        return idReader;
    }

    public void setIdReader(int idReader) {
        this.idReader = idReader;
    }

    @Basic
    @Column(name = "reserved_date", nullable = false)
    public Date getReservedDate() {
        return reservedDate;
    }

    public void setReservedDate(Date reservedDate) {
        this.reservedDate = reservedDate;
    }

    @Basic
    @Column(name = "expire_date", nullable = true)
    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReservedEntity that = (ReservedEntity) o;

        if (idReserved != that.idReserved) return false;
        if (idReader != that.idReader) return false;
        if (isbn != null ? !isbn.equals(that.isbn) : that.isbn != null) return false;
        if (reservedDate != null ? !reservedDate.equals(that.reservedDate) : that.reservedDate != null) return false;
        if (expireDate != null ? !expireDate.equals(that.expireDate) : that.expireDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idReserved;
        result = 31 * result + (isbn != null ? isbn.hashCode() : 0);
        result = 31 * result + idReader;
        result = 31 * result + (reservedDate != null ? reservedDate.hashCode() : 0);
        result = 31 * result + (expireDate != null ? expireDate.hashCode() : 0);
        return result;
    }
}
