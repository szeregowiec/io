package Admin.BookController;

import DataSchema.Base.Database;
import DataSchema.BooksEntity;
import DataSchema.ReadersEntity;
import User.Login.LoginController;
import SearchEngine.FoundBook;
import spark.Route;
import util.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static Main.Application.database;
import static SearchEngine.BookSearchEngine.findBook;

public class ShowBooksController {



    public static Route viewBooks = (request, response) -> {
        if(LoginController.ifUserIsNotLogged(request,response)){
            response.redirect(Constants.LOGIN);
            return "";
        }
        Map<String,Object> model = new HashMap<>();
        model.put("login",request.session().attribute("login"));
       // model.put("login",request.session().attribute("login    "));
        List books;// = new ArrayList();
        if(LoginController.ifItIsNotReader(request,response)){
            books = database.getSession().createQuery("FROM BooksEntity").list();
        }else{
            books = database.getSession().createQuery("FROM BooksEntity where visibility = 1").list();
        }

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
        List books;// = new ArrayList<>();
        if(LoginController.ifItIsNotReader(request,response)){
            books = database.getSession().createQuery("FROM BooksEntity WHERE category = :category").setParameter("category",typeOfBook).list();
        }else{
            books = database.getSession().createQuery("FROM BooksEntity WHERE category = :category and visibility = 1").setParameter("category",typeOfBook).list();

        }
        //List<BooksEntity> books = database.getSession().createQuery("FROM BooksEntity WHERE category = :category").setParameter("category",typeOfBook).list();
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
        if(LoginController.ifItIsNotReader(request,response)){
            model.put("user",false);
            int borrowedAmount;
            try{ //ilość wypożyczonych
                String isbnBook = request.params(":isbn");
                borrowedAmount = Integer.parseInt(database.getSession().createQuery("select count(be.idBorrowed) from BorrowedEntity as be join CopiesEntity as ce on ce.idBook = be.idBook where ce.isbn = :isbn").setParameter("isbn", isbnBook).list().get(0).toString());
                 //+Integer.parseInt(database.getSession().createQuery("select count(re.idReserved) from ReservedEntity as re where re.isbn = :isbn").setParameter("isbn", isbnBook).list().get(0).toString());
            }catch(NullPointerException e){
                borrowedAmount = 0;
            }
            if(borrowedAmount != 0) model.put("delete", false);
            else model.put("delete", true);
        }
        else {
            model.put("user",true);
            List readers = Main.Application.database.getSession().createQuery("FROM ReadersEntity WHERE email = :email").setParameter("email", request.session().attribute("currentUser")).list();
            ReadersEntity reader = ((ReadersEntity) readers.get(0));

            if(reader.getPenalty() != 0) model.put("banned",true);
            else model.put("banned",false);
        }


        String isbnBook = request.params(":isbn");
        List books = database.getSession().createQuery("FROM BooksEntity WHERE isbn = :isbn").setParameter("isbn",isbnBook).list();
        if(books.isEmpty()) response.redirect(Constants.CATALOG);
        BooksEntity book = (BooksEntity)books.get(0);
        if(!LoginController.ifItIsNotReader(request,response) && book.getVisibility() == 0) response.redirect(Constants.CATALOG);
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
        String hideButton;
        if(book.getVisibility() == 1) hideButton = "Ukryj";
        else hideButton = "Pokaż";
        model.put("hButton",hideButton);
        request.session().attribute("hButton",hideButton);
        return util.View.render(request, model, Constants.ONE_BOOK_TEMPLATE);
    };

    public static Route viewFoundBooks = (request, response) -> {
        if(LoginController.ifUserIsNotLogged(request,response)){
            response.redirect(Constants.LOGIN);
            return "";
        }
        Map<String,Object> model = new HashMap<>();
        model.put("login",request.session().attribute("login"));
        String input = request.queryParams("inputFindBook");
        List<BooksEntity> allBooks = database.getSession().createQuery("From BooksEntity ").list();
        Map<String, FoundBook> foundBooks = findBook(allBooks,input);
        List books = new ArrayList();
        for (Map.Entry<String,FoundBook> tmp:foundBooks.entrySet()) {
            books.add(tmp.getValue());
        }
        model.put("books",books);
        return util.View.render(request, model, Constants.VIEW_BOOKS);
    };


}
