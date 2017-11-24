package Book;

import Login.LoginController;
import spark.Route;
import util.Constants;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.staticFiles;

public class ShowBooks {



    public static Route viewBooks = (request, response) -> {
        LoginController.ensureUserIsLoggedIn(request,response);
        Map<String,Object> model = new HashMap<>();
        model.put("login",request.session().attribute("login"));
        return util.View.render(request, model, Constants.VIEW_BOOKS);
    };


    public static Route viewSpecificBooks = (request, response) -> {

        LoginController.ensureUserIsLoggedIn(request,response);
        Map<String,Object> model = new HashMap<>();
        model.put("login",request.session().attribute("login"));
        String typeOfBook = request.params(":name");
        //System.out.println(typeOfBook);



        return util.View.render(request, model, Constants.VIEW_BOOKS);
    };


}
