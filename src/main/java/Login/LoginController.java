package Login;
import DataSchema.EmployeeEntity;
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

    public static boolean ifUserIsNotLogged(Request request, Response response) {
        if (request.session().attribute("currentUser") == null && request.session().attribute("currentEmployee") == null) return true;
        return false;
    };
    public static boolean ifItIsNotReader(Request request, Response response) {
        if (request.session().attribute("currentUser") == null) return true;
        return false;
    };


    public static Route loginIfRegistered = (request, response) -> {
        //Database database= new Database();
        String check = request.queryParams("inputEmployee");
        //System.out.println(check);
        if(check == null){
            List reader = Main.Application.database.getSession().createQuery("FROM ReadersEntity WHERE email = :email and password = :password").setParameter("email", request.queryParams("inputEmail")).setParameter("password", getHashedPassword(request.queryParams("inputPassword"))).list();
            if(!reader.isEmpty())
            {
                ReadersEntity re =  ((ReadersEntity) reader.get(0));
                request.session().attribute("currentUser", re.getEmail());
                request.session().attribute("login", re.getLogin());
                Map<String,Object> model = new HashMap<>();
                model.put("login",request.session().attribute("login"));
                return util.View.render(request, model, Constants.MAIN_TEMPLATE);
            }
        }else{
            List employee = Main.Application.database.getSession().createQuery("FROM EmployeeEntity WHERE email = :email and password = :password").setParameter("email", request.queryParams("inputEmail")).setParameter("password", getHashedPassword(request.queryParams("inputPassword"))).list();
            if(!employee.isEmpty())
            {
                EmployeeEntity ee =  ((EmployeeEntity) employee.get(0));
                request.session().attribute("currentEmployee", ee.getEmail());
                request.session().attribute("login", ee.getLogin());
                Map<String,Object> model = new HashMap<>();
                model.put("login",request.session().attribute("login"));
                return util.View.render(request, model, Constants.MAIN_TEMPLATE);
            }
        }

        Map<String,Object> model = new HashMap<>();
        model.put("wrongEmailOrPassword", true);
        return util.View.render(request, model, Constants.LOGIN_TEMPLATE);

    };

    public static Route start = (request, response) -> {
      if(ifUserIsNotLogged(request,response)){
          response.redirect(Constants.LOGIN);
          return "";
      }
      Map<String,Object> model = new HashMap<>();
      model.put("login",request.session().attribute("login"));
      return util.View.render(request, model, Constants.MAIN_TEMPLATE);
    };


    public static Route logout = (request, response) -> {
        if(ifUserIsNotLogged(request,response)){
            response.redirect(Constants.LOGIN);
            return "";
        }
        request.session().removeAttribute("login");
        request.session().removeAttribute("currentUser");
        request.session().removeAttribute("currentEmployee");
        response.redirect(Constants.LOGIN);
        return null;
    };

}
