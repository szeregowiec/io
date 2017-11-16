package Login;
import Base.Database;
import DataSchema.ReadersEntity;
import spark.Request;
import spark.Response;
import spark.Route;
import util.Constants;

import java.util.HashMap;
import java.util.List;

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
            return util.View.render(request, new HashMap<>(), Constants.VIEW_BOOKS);
        }

        response.redirect(Constants.LOGIN);
        return null;
    };

    public static Route start = (request, response) -> {
      ensureUserIsLoggedIn(request,response);
      return util.View.render(request, new HashMap<>(), Constants.VIEW_BOOKS);
    };


    public static Route Logout = (request, response) -> {
        ensureUserIsLoggedIn(request,response);
        request.session().removeAttribute("currentUser");
        response.redirect(Constants.LOGIN);
        return null;
    };

}
