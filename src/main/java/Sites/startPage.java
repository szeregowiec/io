package Sites;

import Login.LoginController;
import spark.Route;
import util.Constants;

import java.util.HashMap;
import java.util.Map;

public class startPage {


    /*public static Route route = (request, response) -> {
        if(LoginController.ifUserIsNotLogged(request,response)){
            response.redirect(Constants.LOGIN);
            return "";
        }
        Map<String,Object> model = new HashMap<>();
        model.put("login",request.session().attribute("login"));
        return util.View.render(request, model, Constants.MAIN_TEMPLATE);
    };*/


}
