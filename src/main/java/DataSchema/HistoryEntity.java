package DataSchema;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "history", schema = "adbuczek", catalog = "")
public class HistoryEntity {
    private Integer idReader;
    private String isbn;
    private Date returnDate;
    private String title;
    private String authors;
    private int idHistory;

    @Basic
    @Column(name = "title", nullable = true, length = 50)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "authors", nullable = true, length = 50)
    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    @Basic
    @Column(name = "id_reader", nullable = true)
    public Integer getIdReader() {
        return idReader;
    }

    public void setIdReader(Integer idReader) {
        this.idReader = idReader;
    }

    @Basic
    @Column(name = "isbn", nullable = true, length = 50)
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Basic
    @Column(name = "return_date", nullable = true)
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

        HistoryEntity that = (HistoryEntity) o;

        if (idReader != null ? !idReader.equals(that.idReader) : that.idReader != null) return false;
        if (isbn != null ? !isbn.equals(that.isbn) : that.isbn != null) return false;
        if (returnDate != null ? !returnDate.equals(that.returnDate) : that.returnDate != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (authors != null ? !authors.equals(that.authors) : that.authors != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idReader != null ? idReader.hashCode() : 0;
        result = 31 * result + (isbn != null ? isbn.hashCode() : 0);
        result = 31 * result + (returnDate != null ? returnDate.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (authors != null ? authors.hashCode() : 0);
        return result;
    }

    @Id
    @Column(name = "id_history", nullable = false)
    public int getIdHistory() {
        return idHistory;
    }

    public void setIdHistory(int idHistory) {
        this.idHistory = idHistory;
    }
}
