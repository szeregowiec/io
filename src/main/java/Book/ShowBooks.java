package Book;

import Base.Database;
import DataSchema.BooksEntity;
import Login.LoginController;
import spark.Route;
import util.Constants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static Main.Application.database;
import static spark.Spark.redirect;
import static spark.Spark.staticFiles;

public class ShowBooks {



    public static Route viewBooks = (request, response) -> {
        if(LoginController.ifUserIsNotLogged(request,response)){
            response.redirect(Constants.LOGIN);
            return "";
        }
        Map<String,Object> model = new HashMap<>();
        model.put("login",request.session().attribute("login"));
       // model.put("login",request.session().attribute("login    "));
        List<BooksEntity> books = database.getSession().createQuery("FROM BooksEntity").list();
        model.put("books",books);
        request.session().attribute("books",books);
        return util.View.render(request, model, Constants.VIEW_BOOKS);
    };


    public static Route viewSpecificBooks = (request, response) -> {
        if(LoginController.ifUserIsNotLogged(request,response)){
            response.redirect(Constants.LOGIN);
            return "";
        }
        Map<String,Object> model = new HashMap<>();
        model.put("login",request.session().attribute("login"));
        String typeOfBook = request.params(":category");
        List<BooksEntity> books = database.getSession().createQuery("FROM BooksEntity WHERE category = :category").setParameter("category",typeOfBook).list();
        model.put("books",books);
        request.session().attribute("books",books);

        return util.View.render(request, model, Constants.VIEW_BOOKS);
    };

    public static Route viewOneBook = (request, response) -> {
        if(LoginController.ifUserIsNotLogged(request,response)){
            response.redirect(Constants.LOGIN);
            return "";
        }
        Map<String,Object> model = new HashMap<>();
        model.put("login",request.session().attribute("login"));
        if(LoginController.ifItIsNotReader(request,response)) model.put("user",false);
        else model.put("user",true);
        String isbnBook = request.params(":isbn");
        List<BooksEntity> books = database.getSession().createQuery("FROM BooksEntity WHERE isbn = :isbn").setParameter("isbn",isbnBook).list();
        BooksEntity book = books.get(0);
        model.put("book",book);
        request.session().attribute("book",book);
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
        String reservedButton;
        if(copiesAmount > borrowedAmount) reservedButton = "Wypożycz";
        else reservedButton = "Zarezerwuj";
        model.put("rButton",reservedButton);
        request.session().attribute("rButton",reservedButton);
        //System.out.println(copiesAmount);
        return util.View.render(request, model, Constants.ONE_BOOK_TEMPLATE);
    };


}
