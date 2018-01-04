package Login;

import Base.Database;
import DataSchema.*;
import Main.Application;
import User.Borrowed;
import User.Charge;
import User.Reserved;
import spark.Route;
import util.Constants;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static Main.Application.database;

public class UserBookInfo {

    public static Route reservedBooks = (request, response) -> {
        if(LoginController.ifUserIsNotLogged(request,response)){
            response.redirect(Constants.LOGIN);
            return "";
        }
        if(LoginController.ifItIsNotReader(request,response)){
            response.redirect(Constants.START);
            return "";
        }
        Map<String,Object> model = new HashMap<>();
        model.put("login",request.session().attribute("login"));
        // model.put("login",request.session().attribute("login    "));
        //List<ReadersEntity> readers = database.getSession().createQuery("FROM ReadersEntity where email = :email").setParameter("email", request.session().attribute("currentUser")).list();
        ReadersEntity reader = (ReadersEntity) (database.getSession().createQuery("FROM ReadersEntity where email = :email").setParameter("email", request.session().attribute("currentUser")).list()).get(0);
        List<ReservedEntity> books = database.getSession().createQuery("FROM ReservedEntity where idReader = :idReader").setParameter("idReader", reader.getIdReader()).list();
        List<Reserved> reserved = new ArrayList<>();
        for (ReservedEntity book: books) {
            //List<BooksEntity> oneBooks = database.getSession().createQuery("FROM BooksEntity where isbn = :isbn").setParameter("isbn", book.getIsbn()).list();
            BooksEntity oneBook = (BooksEntity) (database.getSession().createQuery("FROM BooksEntity where isbn = :isbn").setParameter("isbn", book.getIsbn()).list()).get(0);
            Reserved tmp = new Reserved(oneBook.getTitle(), oneBook.getAuthors(), book.getReservedDate(), book.getExpireDate(),book.getIdReserved());
            reserved.add(tmp);
        }
        model.put("reserved",reserved);
        request.session().attribute("reserved",reserved);
        return util.View.render(request, model, Constants.RESERVED_BOOKS_TEMPLATE);
    };

    public static Route borrowedBooks = (request, response) -> {
        if(LoginController.ifUserIsNotLogged(request,response)){
            response.redirect(Constants.LOGIN);
            return "";
        }
        if(LoginController.ifItIsNotReader(request,response)){
            response.redirect(Constants.START);
            return "";
        }
        Map<String,Object> model = new HashMap<>();
        model.put("login",request.session().attribute("login"));
        // model.put("login",request.session().attribute("login    "));
       // List<ReadersEntity> readers = database.getSession().createQuery("FROM ReadersEntity where email = :email").setParameter("email", request.session().attribute("currentUser")).list();
        ReadersEntity reader = (ReadersEntity) (database.getSession().createQuery("FROM ReadersEntity where email = :email").setParameter("email", request.session().attribute("currentUser")).list()).get(0);
        List<BorrowedEntity> books = database.getSession().createQuery("FROM BorrowedEntity where idReader = :idReader").setParameter("idReader", reader.getIdReader()).list();
        List<Borrowed> borrowed = new ArrayList<>();
        for (BorrowedEntity book: books) {
           // List<String> oneBooks = database.getSession().createQuery("select be.title FROM BooksEntity as be join CopiesEntity as ce on be.isbn = ce.isbn where ce.idBook = :idBook").setParameter("idBook", book.getIdBook()).list();
            String title = (String)(database.getSession().createQuery("select be.title FROM BooksEntity as be join CopiesEntity as ce on be.isbn = ce.isbn where ce.idBook = :idBook").setParameter("idBook", book.getIdBook()).list()).get(0);
            //oneBooks = database.getSession().createQuery("select be.authors FROM BooksEntity as be join CopiesEntity as ce on be.isbn = ce.isbn where ce.idBook = :idBook").setParameter("idBook", book.getIdBook()).list();
            String authors = (String)(database.getSession().createQuery("select be.authors FROM BooksEntity as be join CopiesEntity as ce on be.isbn = ce.isbn where ce.idBook = :idBook").setParameter("idBook", book.getIdBook()).list()).get(0);
            Borrowed tmp = new Borrowed(title, authors, book.getBorrowedDate(), book.getReturnDate(), book.getIdBorrowed());
            borrowed.add(tmp);
        }
        model.put("borrowed",borrowed);
        request.session().attribute("borrowed",borrowed);
        return util.View.render(request, model, Constants.BORROWED_BOOKS_TEMPLATE);
    };

    public static Route deleteReservedBook = (request, response) -> {
        if(LoginController.ifUserIsNotLogged(request,response)){
            response.redirect(Constants.LOGIN);
            return "";
        }
        if(LoginController.ifItIsNotReader(request,response)){
            response.redirect(Constants.START);
            return "";
        }
        Application.database.getSession().createQuery("delete ReservedEntity where idReserved = :idReserved").setParameter("idReserved",Integer.parseInt(request.params(":id"))).executeUpdate();
        Database.myUpdate();
        response.redirect(Constants.RESERVED_BOOKS);
        return "";
    };

    public static Route prolongBorrowedBook = (request, response) -> {
        if(LoginController.ifUserIsNotLogged(request,response)){
            response.redirect(Constants.LOGIN);
            return "";
        }
        if(LoginController.ifItIsNotReader(request,response)){
            response.redirect(Constants.START);
            return "";
        }
        //Application.database.getSession().createQuery("delete ReservedEntity where idReserved = :idReserved").setParameter("idReserved",Integer.parseInt(request.params(":id"))).executeUpdate();
        //List<BorrowedEntity> books = database.getSession().createQuery("FROM BorrowedEntity WHERE idBorrowed = :id").setParameter("id",Integer.parseInt(request.params(":id"))).list();
        BorrowedEntity book = (BorrowedEntity)(database.getSession().createQuery("FROM BorrowedEntity WHERE idBorrowed = :id").setParameter("id",Integer.parseInt(request.params(":id"))).list()).get(0);
        book.setReturnDate(new Date(new java.util.Date().getTime()+ 604800000));
        Application.database.getSession().update(book);
        Database.myUpdate();
        response.redirect(Constants.BORROWED_BOOKS);
        return "";
    };

    public static Route userPenalties = (request, response) -> {
        if(LoginController.ifUserIsNotLogged(request,response)){
            response.redirect(Constants.LOGIN);
            return "";
        }
        if(LoginController.ifItIsNotReader(request,response)){
            response.redirect(Constants.START);
            return "";
        }
       // List<ReadersEntity> readers = database.getSession().createQuery("FROM ReadersEntity where email = :email").setParameter("email", request.session().attribute("currentUser")).list();
        ReadersEntity reader = (ReadersEntity) (database.getSession().createQuery("FROM ReadersEntity where email = :email").setParameter("email", request.session().attribute("currentUser")).list()).get(0);
        List<BorrowedEntity> books = database.getSession().createQuery("FROM BorrowedEntity where idReader = :idReader and returnDate < current_date()").setParameter("idReader", reader.getIdReader()).list();
        List<Charge> penalties = new ArrayList<>();
        for (BorrowedEntity book: books) {
            //System.out.println(book.getIdBook());
            //List<String> oneBooks = database.getSession().createQuery("select be.title FROM BooksEntity as be join CopiesEntity as ce on be.isbn = ce.isbn where ce.idBook = :idBook").setParameter("idBook", book.getIdBook()).list();
            String oneBook = (String)(database.getSession().createQuery("select be.title FROM BooksEntity as be join CopiesEntity as ce on be.isbn = ce.isbn where ce.idBook = :idBook").setParameter("idBook", book.getIdBook()).list()).get(0);
            Charge tmp = new Charge(oneBook, book.getReturnDate(), (int)Math.abs((book.getReturnDate().getTime() - new java.util.Date().getTime())/100000000));
            penalties.add(tmp);
        }
        Map<String,Object> model = new HashMap<>();
        model.put("login",request.session().attribute("login"));
        model.put("charges",penalties);
        request.session().attribute("charges",penalties);
        int sum = reader.getPenalty();
        model.put("sum",sum);
        request.session().attribute("sum",sum);
        return util.View.render(request, model, Constants.PENALTIES_TEMPLATE);

    };

    public static Route userHistory = (request, response) -> {
        if(LoginController.ifUserIsNotLogged(request,response)){
            response.redirect(Constants.LOGIN);
            return "";
        }
        if(LoginController.ifItIsNotReader(request,response)){
            response.redirect(Constants.START);
            return "";
        }
        Map<String,Object> model = new HashMap<>();
        model.put("login",request.session().attribute("login"));

        ReadersEntity reader = (ReadersEntity) (database.getSession().createQuery("FROM ReadersEntity where email = :email").setParameter("email", request.session().attribute("currentUser")).list()).get(0);
        List<HistoryEntity> historyList = database.getSession().createQuery("from HistoryEntity where idReader = :idReader").setParameter("idReader", reader.getIdReader()).list();
        model.put("history",historyList);
        request.session().attribute("history",historyList);
        return util.View.render(request, model, Constants.HISTORY_TEMPLATE);

    };
}
