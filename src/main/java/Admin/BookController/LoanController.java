package Admin.BookController;

import DataSchema.Base.Database;
import DataSchema.*;
import User.Login.LoginController;
import Main.Application;
import MyRunnable.MailSenderRunnable;
import spark.Filter;
import spark.Route;
import util.Constants;
import java.sql.Date;
import java.util.List;


import static Main.Application.database;


public class LoanController {

    public static Route loanBook = (request, response) -> {

        if(LoginController.ifUserIsNotLogged(request,response)){
            response.redirect(Constants.LOGIN);
            return "";
        }
        String isbnBook = request.params(":isbn");
        int copiesAmount, borrowedAmount;
        Database.myUpdate();
        try{
            copiesAmount = Integer.parseInt(database.getSession().createQuery("select count(isbn) from CopiesEntity where isbn = :isbn").setParameter("isbn", isbnBook).list().get(0).toString());
        }catch(NullPointerException e){
            copiesAmount = 0;
        }
        try{
            borrowedAmount = Integer.parseInt(database.getSession().createQuery("select count(be.idBorrowed) from BorrowedEntity as be join CopiesEntity as ce on ce.idBook = be.idBook where ce.isbn = :isbn").setParameter("isbn", isbnBook).list().get(0).toString()) +
                    Integer.parseInt(database.getSession().createQuery("select count(re.idReserved) from ReservedEntity as re where re.isbn = :isbn and re.expireDate is not null").setParameter("isbn", isbnBook).list().get(0).toString());
        }catch(NullPointerException e){
            borrowedAmount = 0;
        }
        ReservedEntity newReservedBook = new ReservedEntity();
        int max_id;
        try{
            max_id = Integer.parseInt(database.getSession().createQuery("select max(idReserved) from ReservedEntity").list().get(0).toString());
        }catch (NullPointerException e) {
            max_id = 0;
        }
        newReservedBook.setIdReserved(max_id + 1);
       // List<ReadersEntity> re = database.getSession().createQuery("from ReadersEntity where email = :email").setParameter("email",request.session().attribute("currentUser")).list();
        ReadersEntity re1 = (ReadersEntity)(database.getSession().createQuery("from ReadersEntity where email = :email").setParameter("email",request.session().attribute("currentUser")).list()).get(0);
        newReservedBook.setIdReader(re1.getIdReader());
        newReservedBook.setReservedDate(new Date(new java.util.Date().getTime()));
        newReservedBook.setIsbn(isbnBook);

        if(copiesAmount > borrowedAmount) {
            newReservedBook.setExpireDate(new Date(new java.util.Date().getTime()+ 604800000));
            BooksEntity book = (BooksEntity)(database.getSession().createQuery("from BooksEntity where isbn = :isbn").setParameter("isbn",isbnBook).list().get(0));
            String[] emails = {request.session().attribute("currentUser")};
            //MailSender.sendAvailabilityNotification(newReservedBook,book,emails);
            (new Thread(new MailSenderRunnable(1,newReservedBook,book,emails))).start();
        }else{
            newReservedBook.setExpireDate(null);
        }
        Database.myUpdate();
        database.getSession().save("ReservedEntity", newReservedBook);
        Database.myUpdate();
        response.redirect(Constants.CATALOG);
        return "Success";
    };

    public static Filter setNewBorrowed = (request, response) -> {
        if(!LoginController.ifUserIsNotLogged(request,response)){
            //List<BorrowedEntity> b1 = database.getSession().createQuery("from BorrowedEntity where idBorrowed = :idBorrowed").setParameter("idBorrowed",Integer.parseInt(request.params(":id"))).list();
            BorrowedEntity b2 = (BorrowedEntity) (database.getSession().createQuery("from BorrowedEntity where idBorrowed = :idBorrowed").setParameter("idBorrowed",Integer.parseInt(request.params(":id"))).list()).get(0);
            //List<CopiesEntity> c1 = database.getSession().createQuery("from CopiesEntity where idBook = :idBook").setParameter("idBook",b2.getIdBook()).list();
            CopiesEntity c2 = (CopiesEntity) (database.getSession().createQuery("from CopiesEntity where idBook = :idBook").setParameter("idBook",b2.getIdBook()).list()).get(0);
            List<ReservedEntity> reservedBooks = database.getSession().createQuery("from ReservedEntity where isbn = :isbn and expireDate is null").setParameter("isbn", c2.getIsbn()).list();
            if(!reservedBooks.isEmpty()){
                ReservedEntity reservedBook = reservedBooks.get(0);
                reservedBook.setExpireDate(new Date(new java.util.Date().getTime()+ 604800000));
                Application.database.getSession().update(reservedBook);
                Database.myUpdate();
                //List<ReadersEntity> r1 = database.getSession().createQuery("from ReadersEntity where idReader = :idReader").setParameter("idReader", reservedBook.getIdReader()).list();
                ReadersEntity r2 = (ReadersEntity)(database.getSession().createQuery("from ReadersEntity where idReader = :idReader").setParameter("idReader", reservedBook.getIdReader()).list()).get(0);
                //String subject = "Książka czeka na odbiór";
                //List<BooksEntity> be1 = database.getSession().createQuery("from BooksEntity where isbn = :isbn").setParameter("isbn", c2.getIsbn()).list();
                BooksEntity be2 = (BooksEntity)(database.getSession().createQuery("from BooksEntity where isbn = :isbn").setParameter("isbn", c2.getIsbn()).list()).get(0);
                //String body = "Od dnia dzisiejszego do " + reservedBook.getExpireDate() + " w placówce biblioteki czeka na odbiór książka " + be2.getTitle() + " autorstwa " + be2.getAuthors();
                String[] to = {r2.getEmail()};
                //sendAvailabilityNotification(reservedBook, be2, to);
                (new Thread(new MailSenderRunnable(1,reservedBook,be2,to))).start();
                //sendFromGMail(to,subject,body);
            }


        }
    };


}
