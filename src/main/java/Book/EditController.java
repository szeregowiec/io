package Book;

import Base.Database;
import DataSchema.BooksEntity;
import Login.LoginController;
import Main.Application;
import spark.Route;
import util.Constants;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static Main.Application.database;

public class EditController {

    public static Route deleteBook = (request, response) -> {
        if(LoginController.ifUserIsNotLogged(request,response)){
            response.redirect(Constants.LOGIN);
            return "";
        }
        if(!LoginController.ifItIsNotReader(request,response)){
            response.redirect(Constants.START);
            return "";
        }
        //TODO: Delete book
        Application.database.getSession().createQuery("delete BooksEntity where isbn = :isbn").setParameter("isbn",request.params(":isbn")).executeUpdate();
        Application.database.getSession().createQuery("delete CopiesEntity where isbn = :isbn").setParameter("isbn",request.params(":isbn")).executeUpdate();
        Database.myUpdate();
        Path path = FileSystems.getDefault().getPath("src\\main\\resources\\Views\\Covers\\", request.params(":isbn")+".jpg");
        UploadController.deleteCover(path);
        response.redirect(Constants.CATALOG);
        return "";
    };

    public static Route editBook = (request, response) -> {
        if(LoginController.ifUserIsNotLogged(request,response)){
            response.redirect(Constants.LOGIN);
            return "";
        }
        if(!LoginController.ifItIsNotReader(request,response)){
            response.redirect(Constants.START);
            return "";
        }
        Map<String,Object> model = new HashMap<>();

        List<BooksEntity> books = database.getSession().createQuery("FROM BooksEntity WHERE isbn = :isbn").setParameter("isbn",request.params(":isbn")).list();
        BooksEntity book = books.get(0);

        if(request.queryParams().contains("ChangeTitleNew") && !request.queryParams("ChangeTitleNew").equals(book.getTitle())){
            book.setTitle(request.queryParams("ChangeTitleNew"));
            Application.database.getSession().update(book);
            Database.myUpdate();

            model.put("login", request.session().attribute("login"));
            model.put("book", book);
        }
        if(request.queryParams().contains("ChangeAuthorsNew") && !request.queryParams("ChangeAuthorsNew").equals(book.getTitle())){
            book.setAuthors(request.queryParams("ChangeAuthorsNew"));
            Application.database.getSession().update(book);
            Database.myUpdate();
            model.put("login", request.session().attribute("login"));
            model.put("book", book);
        }
        if(request.queryParams().contains("ChangeCategoryNew") && !request.queryParams("ChangeCategoryNew").equals(book.getTitle())){
            book.setCategory(request.queryParams("ChangeCategoryNew"));
            Application.database.getSession().update(book);
            Database.myUpdate();
            model.put("login", request.session().attribute("login"));
            model.put("book", book);
        }
        if(request.queryParams().contains("ChangePublishingHouseNew") && !request.queryParams("ChangePublishingHouseNew").equals(book.getTitle())){
            book.setPublishingHouse(request.queryParams("ChangePublishingHouseNew"));
            Application.database.getSession().update(book);
            Database.myUpdate();
            model.put("login", request.session().attribute("login"));
            model.put("book", book);
        }
        if(request.queryParams().contains("ChangePublishYearNew") && !request.queryParams("ChangePublishYearNew").equals(book.getTitle())){
            book.setPublishYear(request.queryParams("ChangePublishYearNew"));
            Application.database.getSession().update(book);
            Database.myUpdate();
            model.put("login", request.session().attribute("login"));
            model.put("book", book);
        }
        if(request.queryParams().contains("ChangePublishPlaceNew") && !request.queryParams("ChangePublishPlaceNew").equals(book.getTitle())){
            book.setPublishPlace(request.queryParams("ChangePublishPlaceNew"));
            Application.database.getSession().update(book);
            Database.myUpdate();
            model.put("login", request.session().attribute("login"));
            model.put("book", book);
        }
        if(request.queryParams().contains("ChangePagesNew") && !request.queryParams("ChangePagesNew").equals(book.getTitle())){
            book.setPages(request.queryParams("ChangePagesNew"));
            Application.database.getSession().update(book);
            Database.myUpdate();
            model.put("login", request.session().attribute("login"));
            model.put("book", book);
        }
        if(request.queryParams().contains("ChangeDescriptionNew") && !request.queryParams("ChangeDescriptionNew").equals(book.getTitle())){
            book.setDescription(request.queryParams("ChangeDescriptionNew"));
            Application.database.getSession().update(book);
            Database.myUpdate();
            model.put("login", request.session().attribute("login"));
            model.put("book", book);
        }
        if(request.queryParams().contains("ChangeLanguageNew") && !request.queryParams("ChangeLanguageNew").equals(book.getTitle())){
            book.setLanguage(request.queryParams("ChangeLanguageNew"));
            Application.database.getSession().update(book);
            Database.myUpdate();
            model.put("login", request.session().attribute("login"));
            model.put("book", book);
        }
        if(request.queryParams().contains("ChangeAmountNew") && !request.queryParams("ChangeAmountNew").equals(book.getTitle())){
            //TODO
            model.put("login", request.session().attribute("login"));
            model.put("book", book);
        }
        if(request.queryParams().contains("ChangeCoverNew") && !request.queryParams("ChangeCoverNew").equals(book.getTitle())){
            Path path = FileSystems.getDefault().getPath("src\\main\\resources\\Views\\Covers\\", request.params(":isbn")+".jpg");
            UploadController.deleteCover(path);
            UploadController.createCover("ChangeCoverNew", request.params(":isbn"),request,response);
            model.put("login", request.session().attribute("login"));
            model.put("book", book);
        }

        int copiesAmount;
        //Database.myUpdate();
        try{
            copiesAmount = Integer.parseInt(database.getSession().createQuery("select count(isbn) from CopiesEntity where isbn = :isbn").setParameter("isbn", request.params(":isbn")).list().get(0).toString());
        }catch(NullPointerException e){
            copiesAmount = 0;
        }
        model.put("amount", copiesAmount);
        request.session().attribute("amount", copiesAmount);
        return util.View.render(request, model, Constants.EDIT_BOOK_TEMPLATE);
        //TODO: Edit book information

    };

    public static Route giveInformation = (request, response) -> {
        if(LoginController.ifUserIsNotLogged(request,response)){
            response.redirect(Constants.LOGIN);
            return "";
        }
        if(!LoginController.ifItIsNotReader(request,response)){
            response.redirect(Constants.START);
            return "";
        }
        Map<String,Object> model = new HashMap<>();
        model.put("login",request.session().attribute("login    "));
        List<BooksEntity> books = database.getSession().createQuery("FROM BooksEntity WHERE isbn = :isbn").setParameter("isbn",request.params(":isbn")).list();
            BooksEntity book = books.get(0);
        model.put("book", book);
        request.session().attribute("book",book);
        int copiesAmount;
        //Database.myUpdate();
        try{
            copiesAmount = Integer.parseInt(database.getSession().createQuery("select count(isbn) from CopiesEntity where isbn = :isbn").setParameter("isbn", request.params(":isbn")).list().get(0).toString());
        }catch(NullPointerException e){
            copiesAmount = 0;
        }
        model.put("amount", copiesAmount);
        request.session().attribute("amount", copiesAmount);
        return util.View.render(request, model, Constants.EDIT_BOOK_TEMPLATE);
    };
}
