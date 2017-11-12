package Login;

import spark.Route;
import util.Constants;

import java.util.HashMap;

public class Register {

    public static Route register = (request, response) -> {

    return "Zapiszemy tutaj użytkownika";
    };


    public static Route giveInformation = (request, response) -> {
        return util.View.render(request, new HashMap<>(), Constants.REGISTER_TEMPLATE);
    };

}
