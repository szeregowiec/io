package MyRunnable;

import DataSchema.BooksEntity;
import DataSchema.ReservedEntity;

import java.util.concurrent.TimeUnit;

import static MyRunnable.MailSender.*;

public class MailSenderRunnable implements Runnable{
    private int option;
    private ReservedEntity reservedBook;
    private BooksEntity book;
    private String[] emails;
    public void run() {
        switch (option) {
            case 1:
                sendAvailabilityNotification(reservedBook,book,emails);
                break;
            case 2:
                sendCancelNotification(book,emails);
                break;
            case 3:
                sendNotAvailabilityNotification(book,emails);
            default:
                break;
        }
    }

    public MailSenderRunnable(int option, ReservedEntity reservedBook, BooksEntity book, String[] emails) {
        this.option = option;
        this.reservedBook = reservedBook;
        this.book = book;
        this.emails = emails;
    }
}
