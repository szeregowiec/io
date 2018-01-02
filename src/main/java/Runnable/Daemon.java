package Runnable;

import Base.Database;

import DataSchema.BorrowedEntity;
import DataSchema.ReadersEntity;
import DataSchema.ReservedEntity;
import Main.Application;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;
import java.util.*;



import static Main.Application.database;
import static Runnable.MailSender.sendFromGMail;

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
    private static void sendReturnNotification(){
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
            //System.out.println(days);
            if(days == 1.0){

                List<ReadersEntity> readers = database.getSession().createQuery("from ReadersEntity where idReader = :id").setParameter("id", borrowedBook.getIdReader()).list();
                ReadersEntity reader = readers.get(0);
                List<String> book = database.getSession().createQuery("select be.title FROM BooksEntity as be join CopiesEntity as ce on be.isbn = ce.isbn where ce.idBook = :idBook").setParameter("idBook", borrowedBook.getIdBook()).list();
                String title = book.get(0);
                book = database.getSession().createQuery("select be.authors FROM BooksEntity as be join CopiesEntity as ce on be.isbn = ce.isbn where ce.idBook = :idBook").setParameter("idBook", borrowedBook.getIdBook()).list();
                String authors = book.get(0);
                String[] to = {reader.getEmail()};
                body = "W dniu jutrzejszym upływa termin oddania książki " + title + "autorstwa " + authors;
                sendFromGMail(to,subject,body);
            }

        }

    }
    private static void addPenalties(){
        List<ReadersEntity> readers = database.getSession().createQuery("from ReadersEntity").list();
        List<BorrowedEntity> borrowedBooks = new ArrayList<>();
        for (ReadersEntity reader : readers) {
            borrowedBooks = database.getSession().createQuery("from BorrowedEntity where returnDate < current_date() and idReader = :idReader").setParameter("idReader", reader.getIdReader()).list();
            for (BorrowedEntity borrowedBook : borrowedBooks) {
                reader.setPenalty(reader.getPenalty() + 1);
            }
            Application.database.getSession().update(reader);
            Database.myUpdate();
        }
    }
    private static void removeExpiredReservation(){
        Application.database.getSession().createQuery("delete ReservedEntity where expireDate < current_date() ").executeUpdate();
        Database.myUpdate();
    }


}
