package Login;
import Base.Database;
import DataSchema.ReadersEntity;
import spark.Request;
import spark.Response;
import spark.Route;
import util.Constants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static util.Password.getHashedPassword;


public class LoginController {

    public static void ensureUserIsLoggedIn(Request request, Response response) {
        if (request.session().attribute("currentUser") == null) {
            response.redirect(Constants.LOGIN);
        }
    };

    public static Route loginIfRegistered = (request, response) -> {
        //Database database= new Database();

        List reader = Main.Application.database.getSession().createQuery("FROM ReadersEntity WHERE email = :email and password = :password").setParameter("email", request.queryParams("inputEmail")).setParameter("password", getHashedPassword(request.queryParams("inputPassword"))).list();
        if(!reader.isEmpty())
        {
            ReadersEntity re =  ((ReadersEntity) reader.get(0));
            request.session().attribute("currentUser", re.getEmail());
            request.session().attribute("login", re.getLogin());
            Map<String,Object> model = new HashMap<>();
            model.put("login",request.session().attribute("login"));
            return util.View.render(request, model, Constants.VIEW_BOOKS);
        }
        Map<String,Object> model = new HashMap<>();
        model.put("wrongEmailOrPassword", true);
        return util.View.render(request, model, Constants.LOGIN_TEMPLATE);

    };

    public static Route start = (request, response) -> {
      ensureUserIsLoggedIn(request,response);
      Map<String,Object> model = new HashMap<>();
      model.put("login",request.session().attribute("login"));
      return util.View.render(request, model, Constants.VIEW_BOOKS);
    };


    public static Route logout = (request, response) -> {
        ensureUserIsLoggedIn(request,response);
        request.session().removeAttribute("login");
        request.session().removeAttribute("currentUser");
        response.redirect(Constants.LOGIN);
        return null;
    };

}
