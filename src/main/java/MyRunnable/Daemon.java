package MyRunnable;

import DataSchema.Base.Database;

import DataSchema.BorrowedEntity;
import DataSchema.ReadersEntity;
import Main.Application;
import java.util.concurrent.TimeUnit;
import java.util.*;



import static Main.Application.database;

import static MyRunnable.MailSenderRunnable.sendReturnNotification;


/**
 * Klasa która odpowiada za wysyłanie powiadomień, nalicznie kar oraz kasowanie przestarzałych rezerwacji co 24 godziny
 */
public class Daemon implements Runnable {


    public void run() {
        while(true){
                addPenalties();
                sendReturnNotification();
                removeExpiredReservation();
            try{
                TimeUnit.HOURS.sleep(24);
            }catch (InterruptedException e){
                e.printStackTrace();
            }

        }

    }

    /**
     * Nalicza kary
     */
    private static void addPenalties(){
        List<ReadersEntity> readers = database.getSession().createQuery("from ReadersEntity").list();
        List<BorrowedEntity> borrowedBooks;// = new ArrayList<>();
        for (ReadersEntity reader : readers) {
            borrowedBooks = database.getSession().createQuery("from BorrowedEntity where returnDate < current_date() and idReader = :idReader").setParameter("idReader", reader.getIdReader()).list();
            for (BorrowedEntity borrowedBook : borrowedBooks) {
                reader.setPenalty(reader.getPenalty() + 1);
            }
            Application.database.getSession().update(reader);
            Database.myUpdate();
        }
    }

    /**
     * Usuwa przestarzałe rezerwacje
     */
    private static void removeExpiredReservation(){
        Application.database.getSession().createQuery("delete ReservedEntity where expireDate < current_date() ").executeUpdate();
        Database.myUpdate();
    }


}
