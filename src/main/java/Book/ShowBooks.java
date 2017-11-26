package Book;

import DataSchema.BooksEntity;
import Login.LoginController;
import spark.Route;
import util.Constants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static Main.Application.database;
import static spark.Spark.staticFiles;

public class ShowBooks {



    public static Route viewBooks = (request, response) -> {
        if(LoginController.ifUserIsNotLogged(request,response)){
            response.redirect(Constants.LOGIN);
            return "";
        }
        Map<String,Object> model = new HashMap<>();
        model.put("login",request.session().attribute("login    "));
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
        String typeOfBook = request.params(":isbn");
        return util.View.render(request, model, Constants.VIEW_BOOKS);
    };


}
