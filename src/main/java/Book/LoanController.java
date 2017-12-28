package Book;


import Base.Database;
import DataSchema.*;
import Login.LoginController;

import spark.Route;
import util.Constants;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        List<ReadersEntity> re = database.getSession().createQuery("from ReadersEntity where email = :email").setParameter("email",request.session().attribute("currentUser")).list();
        ReadersEntity re1 = re.get(0);
        newReservedBook.setIdReader(re1.getIdReader());
        newReservedBook.setReservedDate(new Date(new java.util.Date().getTime()));
        ///newReservedBook.setExpireDate(new Date(new java.util.Date().getTime()+ 604800000));
        newReservedBook.setIsbn(isbnBook);

        if(copiesAmount > borrowedAmount) {
            newReservedBook.setExpireDate(new Date(new java.util.Date().getTime()+ 604800000));
           /*BorrowedEntity newBorrowedBook = new BorrowedEntity();
            int max_id;
            try{
                max_id = Integer.parseInt(database.getSession().createQuery("select max(idBorrowed) from BorrowedEntity").list().get(0).toString());
            }catch (NullPointerException e) {
                max_id = 0;
            }
            //System.out.println(max_id);
            newBorrowedBook.setIdBorrowed(max_id + 1);
            List<ReadersEntity> re = database.getSession().createQuery("from ReadersEntity where email = :email").setParameter("email",request.session().attribute("currentUser")).list();
            ReadersEntity re1 = re.get(0);
            newBorrowedBook.setIdReader(re1.getIdReader());
            newBorrowedBook.setBorrowedDate(new Date(new java.util.Date().getTime()));
            newBorrowedBook.setReturnDate(new Date(new java.util.Date().getTime()+ 604800000));
            List<Integer> ce = database.getSession().createQuery("select idBook from CopiesEntity where isbn = :isbn").setParameter("isbn",isbnBook).list();
            List<Integer> be = database.getSession().createQuery("select be.idBook from BorrowedEntity as be join CopiesEntity as ce on be.idBook = ce.idBook where ce.isbn = :isbn").setParameter("isbn",isbnBook).list();
            ce.removeAll(be);
            newBorrowedBook.setIdBook(ce.get(0));
            database.getSession().save("BorrowedEntity", newBorrowedBook);*/
        }else{
            newReservedBook.setExpireDate(null);
        }
        Database.myUpdate();
            database.getSession().save("ReservedEntity", newReservedBook);
        Database.myUpdate();
        response.redirect(Constants.CATALOG);
        return "Success";
    };

    public static Route test = (request, response) -> {
        System.out.println("test");
        return "";
    };
}
