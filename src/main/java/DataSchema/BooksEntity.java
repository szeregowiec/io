package DataSchema;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "books", schema = "adbuczek", catalog = "")
public class BooksEntity {
    private String title;
    private String authors;
    private String category;
    private String publishingHouse;
    private Date publishYear;
    private String publishPlace;
    private String pages;
    private String description;
    private String language;
    private String isbn;

    @Basic
    @Column(name = "title", nullable = false, length = 50)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "authors", nullable = false, length = 50)
    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    @Basic
    @Column(name = "category", nullable = false, length = 50)
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Basic
    @Column(name = "publishing_house", nullable = false, length = 50)
    public String getPublishingHouse() {
        return publishingHouse;
    }

    public void setPublishingHouse(String publishingHouse) {
        this.publishingHouse = publishingHouse;
    }

    @Basic
    @Column(name = "publish_year", nullable = false)
    public Date getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(Date publishYear) {
        this.publishYear = publishYear;
    }

    @Basic
    @Column(name = "publish_place", nullable = false, length = 50)
    public String getPublishPlace() {
        return publishPlace;
    }

    public void setPublishPlace(String publishPlace) {
        this.publishPlace = publishPlace;
    }

    @Basic
    @Column(name = "pages", nullable = false, length = 50)
    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    @Basic
    @Column(name = "description", nullable = false, length = 50)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "language", nullable = false, length = 50)
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Id
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

        BooksEntity that = (BooksEntity) o;

        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (authors != null ? !authors.equals(that.authors) : that.authors != null) return false;
        if (category != null ? !category.equals(that.category) : that.category != null) return false;
        if (publishingHouse != null ? !publishingHouse.equals(that.publishingHouse) : that.publishingHouse != null)
            return false;
        if (publishYear != null ? !publishYear.equals(that.publishYear) : that.publishYear != null) return false;
        if (publishPlace != null ? !publishPlace.equals(that.publishPlace) : that.publishPlace != null) return false;
        if (pages != null ? !pages.equals(that.pages) : that.pages != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (language != null ? !language.equals(that.language) : that.language != null) return false;
        if (isbn != null ? !isbn.equals(that.isbn) : that.isbn != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (authors != null ? authors.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (publishingHouse != null ? publishingHouse.hashCode() : 0);
        result = 31 * result + (publishYear != null ? publishYear.hashCode() : 0);
        result = 31 * result + (publishPlace != null ? publishPlace.hashCode() : 0);
        result = 31 * result + (pages != null ? pages.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (language != null ? language.hashCode() : 0);
        result = 31 * result + (isbn != null ? isbn.hashCode() : 0);
        return result;
    }
}
