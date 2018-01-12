package MyRunnable;

import DataSchema.BooksEntity;
import DataSchema.BorrowedEntity;
import DataSchema.ReadersEntity;
import DataSchema.ReservedEntity;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;


import static Main.Application.database;


/**
 * Klasa wysyłająca powidomienia na podstawie klasy MailSender która robi to w oddzielnym wątku aby nie
 */
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

    /**
     *
     * @param option rodzaj powiadomienia
     * @param reservedBook  informacje o dacie wygaśnięcia może być null dla odpowiednich powiadomień
     * @param book informacje o książce
     * @param emails lista odbiorców
     */
    public MailSenderRunnable(int option, ReservedEntity reservedBook, BooksEntity book, String[] emails) {
        this.option = option;
        this.reservedBook = reservedBook;
        this.book = book;
        this.emails = emails;
    }


    private static final String USER_NAME = "biblioteka.agh";  // GMail user name (just the part before "@gmail.com")
    private static final String PASSWORD = "r1fa035ab"; // GMail password

    /**
     * Wysyła maile
     * @param to odbiorcy
     * @param subject temat
     * @param body treść
     */
    private static void sendFromGMail(String[] to, String subject, String body) {
        String from = USER_NAME;
        String pass = PASSWORD;
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(from, "Biblioteka Główna"));
            InternetAddress[] toAddress = new InternetAddress[to.length];

            // To get the array of addresses
            for( int i = 0; i < to.length; i++ ) {
                toAddress[i] = new InternetAddress(to[i]);
            }

            for(InternetAddress address : toAddress) {
                message.addRecipient(Message.RecipientType.TO, address);
            }

            message.setSubject(subject);
            message.setText(body);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Geneuruje powiadomienie o pojawieniu się książki w serwisie
     * @param reservedBook do pobrania daty wygaśnięcia rezerwacji
     * @param book informacja o jaką książke chodzi
     * @param emailList lista adresów do których zostanie wysłane powiadomienie
     */
    private static void sendAvailabilityNotification(ReservedEntity reservedBook, BooksEntity book, String[] emailList){
        String subject = "Książka czeka na odbiór";
        String body = "Od dnia dzisiejszego do " + reservedBook.getExpireDate() + " w placówce biblioteki czeka na odbiór książka " + book.getTitle() + " autorstwa " + book.getAuthors();
        sendFromGMail(emailList, subject, body);

    }

    /**
     * Generuje powiadomienie o anulowaniu rezerwacji
     * @param book informacja o jaką książke chodzi
     * @param emailList lista sdresów do których zostanie wysłane powaidomienie
     */
    private static void sendCancelNotification(BooksEntity book, String[] emailList){
        String subject = "Anulowana rezerwacja";
        String body = "Z powodu wycofania książki " + book.getTitle() + " autorstwa " + book.getAuthors() + " pańska rezerwacja została anulowana.\nZa utrudnienia przepraszamy.";
        sendFromGMail(emailList,subject,body);
    }

    /**
     * Generuje powiadominie o chwilowym braku dostępności ksiązki w serwisie
     * @param book informacja o jaką książke chodzi
     * @param emailList lista sdresów do których zostanie wysłane powaidomienie
     */
    private static void sendNotAvailabilityNotification(BooksEntity book, String[] emailList){
        String subject = "Książka chwilowo niedostępna";
        String body = "Książka " + book.getTitle() + " autorstwa" + book.getAuthors() +" nie jest chwilowo dostępna w naszej placówce.\nPowiadomimy pana/panią, gdy książka będzie dostępna.";
        sendFromGMail(emailList,subject,body);
    }

    /**
     * Generuje powiadomienie o zbliżającym się terminie oddania książki
     */
    public static void sendReturnNotification(){
        String subject = "Przypomnienie o zwrocie książki";
        String body;
        List<BorrowedEntity> borrowedBooks = database.getSession().createQuery("from BorrowedEntity").list();
        for (BorrowedEntity borrowedBook: borrowedBooks) {
            //long days = TimeUnit.DAYS.convert(borrowedBook.getReturnDate().getTime() - new java.util.Date().getTime(), TimeUnit.MILLISECONDS);
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

            Date today = new Date();
            Date todayWithZeroTime = new Date();
            try {
                todayWithZeroTime = formatter.parse(formatter.format(today));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            float days = ((borrowedBook.getReturnDate().getTime() - todayWithZeroTime.getTime()) / (1000*60*60*24));
            if(days == 1.0){
                //List<ReadersEntity> readers = database.getSession().createQuery("from ReadersEntity where idReader = :id").setParameter("id", borrowedBook.getIdReader()).list();
                ReadersEntity reader = (ReadersEntity) (database.getSession().createQuery("from ReadersEntity where idReader = :id").setParameter("id", borrowedBook.getIdReader()).list()).get(0);
                //List<String> book = database.getSession().createQuery("select be.title FROM BooksEntity as be join CopiesEntity as ce on be.isbn = ce.isbn where ce.idBook = :idBook").setParameter("idBook", borrowedBook.getIdBook()).list();
                String title = (String)(database.getSession().createQuery("select be.title FROM BooksEntity as be join CopiesEntity as ce on be.isbn = ce.isbn where ce.idBook = :idBook").setParameter("idBook", borrowedBook.getIdBook()).list()).get(0);
                // book = database.getSession().createQuery("select be.authors FROM BooksEntity as be join CopiesEntity as ce on be.isbn = ce.isbn where ce.idBook = :idBook").setParameter("idBook", borrowedBook.getIdBook()).list();
                String authors = (String)(database.getSession().createQuery("select be.authors FROM BooksEntity as be join CopiesEntity as ce on be.isbn = ce.isbn where ce.idBook = :idBook").setParameter("idBook", borrowedBook.getIdBook()).list()).get(0);
                String[] to = {reader.getEmail()};
                body = "W dniu jutrzejszym upływa termin oddania książki " + title + " autorstwa " + authors;
                sendFromGMail(to,subject,body);
            }

        }

    }
}
