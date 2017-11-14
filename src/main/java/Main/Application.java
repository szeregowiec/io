package Main;

import Base.Database;
import DataSchema.ReadersEntity;
import Login.LoginController;
import Login.Register;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import spark.Filter;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import util.Constants;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static spark.Spark.*;
import org.apache.velocity.*;
import spark.template.velocity.*;
import util.View;

public class Application {

    public static Database database;

    public static void main(String[] args) {

        staticFiles.location("/Views");
        port(8000);
        init();
        database = new Database();

        get("/", (req, res) -> "Hello world");

        get(Constants.LOGIN, (request, response) -> {
            return View.render(request, new HashMap<>(),Constants.LOGIN_TEMPLATE);
        });

        post(Constants.START, LoginController.loginIfRegistered);
        get(Constants.REGISTER, Register.giveInformation);
        post(Constants.REGISTER, Register.register);

    }

}
