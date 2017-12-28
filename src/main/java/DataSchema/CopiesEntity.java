package DataSchema;

import javax.persistence.*;

@Entity
@Table(name = "copies", schema = "adbuczek", catalog = "")
public class CopiesEntity {
    private int idBook;
    private String isbn;

    @Id
    @Column(name = "id_book", nullable = false)
    public int getIdBook() {
        return idBook;
    }

    public void setIdBook(int idBook) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CopiesEntity that = (CopiesEntity) o;

        if (idBook != that.idBook) return false;
        if (isbn != null ? !isbn.equals(that.isbn) : that.isbn != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idBook;
        result = 31 * result + (isbn != null ? isbn.hashCode() : 0);
        return result;
    }
}
