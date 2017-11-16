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

    public static Route loginIfRegistered = (request, response) -> {
        //Database database= new Database();

        List reader = Main.Application.database.getSession().createQuery("FROM ReadersEntity WHERE email = :email and password = :password").setParameter("email", request.queryParams("inputEmail")).setParameter("password", getHashedPassword(request.queryParams("inputPassword"))).list();
        if(reader.isEmpty()){
            return "You cannot log in. You have given us wrong email or password. Sorry!";
        }else {
            ReadersEntity re =  ((ReadersEntity) reader.get(0));

            return util.View.render(request, new HashMap<>(), Constants.VIEW_BOOKS);
        }
    };

}
