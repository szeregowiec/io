package Admin;

import DataSchema.Base.Database;
import DataSchema.*;
import User.Login.LoginController;
import Main.Application;

import spark.Route;
import util.Constants;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static Main.Application.database;

public class AdminBookInfo {


    public static Route borrowing = (request, response) -> {
        if(LoginController.ifUserIsNotLogged(request,response)){
            response.redirect(Constants.LOGIN);
            return "";
        }
        if(!LoginController.ifItIsNotReader(request,response)){
            response.redirect(Constants.START);
            return "";
        }
        Map<String,Object> model = new HashMap<>();
        model.put("login",request.session().attribute("login"));
        List<ReservedEntity> books = database.getSession().createQuery("FROM ReservedEntity where expireDate is not null").list();
        List<Reservation> reserved = new ArrayList<>();
        for (ReservedEntity book: books) {
            //List<ReadersEntity> user = database.getSession().createQuery("FROM ReadersEntity where idReader = :idReader").setParameter("idReader", book.getIdReader()).list();
            ReadersEntity oneUser = (ReadersEntity)(database.getSession().createQuery("FROM ReadersEntity where idReader = :idReader").setParameter("idReader", book.getIdReader()).list()).get(0);
            //List<BooksEntity> oneBooks = database.getSession().createQuery("FROM BooksEntity where isbn = :isbn").setParameter("isbn", book.getIsbn()).list();
            BooksEntity oneBook = (BooksEntity)(database.getSession().createQuery("FROM BooksEntity where isbn = :isbn").setParameter("isbn", book.getIsbn()).list()).get(0);
            Reservation tmp = new Reservation(oneBook.getTitle(), oneBook.getAuthors(), oneUser.getEmail()+ " " +oneUser.getName() + " " + oneUser.getSurname(), book.getIdReserved());
            reserved.add(tmp);
        }
        model.put("confirmBorrow",reserved);
        request.session().attribute("confirmBorrow",reserved);
        return util.View.render(request, model, Constants.RESERVATION_LIST_TEMPLATE);
    };

    public static Route returning = (request, response) -> {
        if(LoginController.ifUserIsNotLogged(request,response)){
            response.redirect(Constants.LOGIN);
            return "";
        }
        if(!LoginController.ifItIsNotReader(request,response)){
            response.redirect(Constants.START);
            return "";
        }
        Map<String,Object> model = new HashMap<>();
        model.put("login",request.session().attribute("login"));
        List<BorrowedEntity> books = database.getSession().createQuery("FROM BorrowedEntity").list();
        List<Reservation> reserved = new ArrayList<>();
        for (BorrowedEntity book: books) {
            //List<ReadersEntity> user = database.getSession().createQuery("FROM ReadersEntity where idReader = :idReader").setParameter("idReader", book.getIdReader()).list();
            ReadersEntity oneUser = (ReadersEntity)(database.getSession().createQuery("FROM ReadersEntity where idReader = :idReader").setParameter("idReader", book.getIdReader()).list()).get(0);
            //List<CopiesEntity> copies = database.getSession().createQuery("FROM CopiesEntity where idBook = :idBook").setParameter("idBook", book.getIdBook()).list();
            CopiesEntity copy = (CopiesEntity) (database.getSession().createQuery("FROM CopiesEntity where idBook = :idBook").setParameter("idBook", book.getIdBook()).list()).get(0);
            //List<BooksEntity> oneBooks = database.getSession().createQuery("FROM BooksEntity where isbn = :isbn").setParameter("isbn", copy.getIsbn()).list();
            BooksEntity oneBook = (BooksEntity)(database.getSession().createQuery("FROM BooksEntity where isbn = :isbn").setParameter("isbn", copy.getIsbn()).list()).get(0);
            Reservation tmp = new Reservation(oneBook.getTitle(), oneBook.getAuthors(), oneUser.getEmail()+ " " +oneUser.getName() + " " + oneUser.getSurname(), book.getIdBorrowed());
            reserved.add(tmp);
        }
        model.put("confirmReturn",reserved);
        request.session().attribute("confirmReturn",reserved);
        return util.View.render(request, model, Constants.RETURNING_LIST_TEMPLATE);
    };

    public static Route payments = (request, response) -> {
        if(LoginController.ifUserIsNotLogged(request,response)){
            response.redirect(Constants.LOGIN);
            return "";
        }
        if(!LoginController.ifItIsNotReader(request,response)){
            response.redirect(Constants.START);
            return "";
        }
        Map<String,Object> model = new HashMap<>();
        model.put("login",request.session().attribute("login"));
        List readers = database.getSession().createQuery("FROM ReadersEntity where penalty > 0").list();
        model.put("confirmCharge",readers);
        request.session().attribute("confirmCharge",readers);
        return util.View.render(request, model, Constants.PAYMENTS_LIST_TEMPLATE);
    };

    public static Route confirmBorrowing = (request, response) -> {
        Database.myUpdate();
        if(LoginController.ifUserIsNotLogged(request,response)){
            response.redirect(Constants.LOGIN);
            return "";
        }
        if(!LoginController.ifItIsNotReader(request,response)){
            response.redirect(Constants.START);
            return "";
        }
        BorrowedEntity newBorrowedBook = new BorrowedEntity();
        int max_id;
        try{
            max_id = Integer.parseInt(database.getSession().createQuery("select max(idBorrowed) from BorrowedEntity").list().get(0).toString());
        }catch (NullPointerException e) {
            max_id = 0;
        }
        List<ReservedEntity> reservationList = Application.database.getSession().createQuery("FROM ReservedEntity where idReserved = :idReserved").setParameter("idReserved",Integer.parseInt(request.params(":id"))).list();
        ReservedEntity re1 = reservationList.get(0);
        newBorrowedBook.setIdBorrowed(max_id + 1);

        newBorrowedBook.setIdReader(re1.getIdReader());
        newBorrowedBook.setBorrowedDate(new Date(new java.util.Date().getTime()));
        newBorrowedBook.setReturnDate(new Date(new java.util.Date().getTime()+ 604800000));
        List<Integer> ce = database.getSession().createQuery("select idBook from CopiesEntity where isbn = :isbn").setParameter("isbn",re1.getIsbn()).list();
        List<Integer> be = database.getSession().createQuery("select be.idBook from BorrowedEntity as be join CopiesEntity as ce on be.idBook = ce.idBook where ce.isbn = :isbn").setParameter("isbn",re1.getIsbn()).list();
        ce.removeAll(be);
        newBorrowedBook.setIdBook(ce.get(0));
        database.getSession().save("BorrowedEntity", newBorrowedBook);

        Application.database.getSession().createQuery("delete ReservedEntity where idReserved = :idReserved").setParameter("idReserved",Integer.parseInt(request.params(":id"))).executeUpdate();
        Database.myUpdate();
        response.redirect(Constants.BORROWING);
        return "";
    };

    public static Route confirmReturning = (request, response) -> {
        Database.myUpdate();
        if(LoginController.ifUserIsNotLogged(request,response)){
            response.redirect(Constants.LOGIN);
            return "";
        }
        if(!LoginController.ifItIsNotReader(request,response)){
            response.redirect(Constants.START);
            return "";
        }
        int idReader = (int)(database.getSession().createQuery("select idReader from BorrowedEntity where idBorrowed = :idBorrowed").setParameter("idBorrowed",Integer.parseInt(request.params(":id"))).list()).get(0);
        int idBook = (int)(database.getSession().createQuery("select idBook from BorrowedEntity where idBorrowed = :idBorrowed").setParameter("idBorrowed",Integer.parseInt(request.params(":id"))).list()).get(0);
        String isbn = (String)(database.getSession().createQuery("select isbn from CopiesEntity where idBook = :idBook").setParameter("idBook",idBook).list()).get(0);
        BooksEntity book = (BooksEntity)(database.getSession().createQuery("from BooksEntity where isbn = :isbn").setParameter("isbn",isbn).list()).get(0);
        int max_id;
        try{
            max_id = Integer.parseInt(database.getSession().createQuery("select max(idHistory) from HistoryEntity ").list().get(0).toString()); // wysypie się jak nie ma żadnego użytkownika
        }catch (NullPointerException e){
            max_id = 0;
        }

        HistoryEntity history = new HistoryEntity();
        history.setReturnDate(new Date(new java.util.Date().getTime()));
        history.setIdReader(idReader);
        history.setAuthors(book.getAuthors());
        history.setIsbn(isbn);
        history.setTitle(book.getTitle());
        history.setIdHistory(max_id + 1);
        database.getSession().save("HistoryEntity", history);
        Application.database.getSession().createQuery("delete BorrowedEntity where idBorrowed = :idBorrowed").setParameter("idBorrowed",Integer.parseInt(request.params(":id"))).executeUpdate();
        Database.myUpdate();
        response.redirect(Constants.RETURNING);
        return "";
    };

    public static Route confirmPayment = (request, response) -> {
        if(LoginController.ifUserIsNotLogged(request,response)){
            response.redirect(Constants.LOGIN);
            return "";
        }
        if(!LoginController.ifItIsNotReader(request,response)){
            response.redirect(Constants.START);
            return "";
        }
        List users = database.getSession().createQuery("FROM ReadersEntity WHERE idReader = :id").setParameter("id",Integer.parseInt(request.params(":id"))).list();
        ReadersEntity user = (ReadersEntity)users.get(0);
        user.setPenalty(0);
        Application.database.getSession().update(user);
        Database.myUpdate();
        response.redirect(Constants.PAYMENT);
        return "";
    };

}
