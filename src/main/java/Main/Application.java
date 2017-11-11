package Main;

import Base.Database;
import DataSchema.ReadersEntity;
import Login.LoginController;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import spark.ModelAndView;
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
        database = new Database();
        staticFiles.location("/Views");

        port(8000);
        get("/", (req, res) -> "Hello world");
        get(Constants.INDEX, (request, response) -> {
            return View.render(request, new HashMap<>(),Constants.TEMPLATE);
        });

        post(Constants.START, LoginController.loginIfRegistered);



    }



}
