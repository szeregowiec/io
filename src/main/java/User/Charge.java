package User;

import java.sql.Date;


/**
 * Klasa pomocnicza do wyświetlania odpowidniego obiektu w velocity
 */
public class Charge {

    private String title;
    private Date returnDate;
    private long charge;

    public String getTitle() {
        return title;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public long getCharge() {
        return charge;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public void setCharge(long charge) {
        this.charge = charge;
    }

    public Charge(String title, Date returnDate, long charge) {
        this.title = title;
        this.returnDate = returnDate;
        this.charge = charge;
    }
}
