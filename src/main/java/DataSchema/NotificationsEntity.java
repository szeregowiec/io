package DataSchema;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "notifications", schema = "adbuczek", catalog = "")
public class NotificationsEntity {
    private String idNotification;
    private String idReader;
    private String content;

    @Basic
    @Column(name = "id_notification", nullable = false, length = 50)
    public String getIdNotification() {
        return idNotification;
    }

    public void setIdNotification(String idNotification) {
        this.idNotification = idNotification;
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
    @Column(name = "content", nullable = false, length = 50)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NotificationsEntity that = (NotificationsEntity) o;

        if (idNotification != null ? !idNotification.equals(that.idNotification) : that.idNotification != null)
            return false;
        if (idReader != null ? !idReader.equals(that.idReader) : that.idReader != null) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idNotification != null ? idNotification.hashCode() : 0;
        result = 31 * result + (idReader != null ? idReader.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }
}
