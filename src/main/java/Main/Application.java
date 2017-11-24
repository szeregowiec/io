package Main;

import Base.Database;
import Book.ShowBooks;
import Book.UploadController;
import DataSchema.ReadersEntity;
import Login.LoginController;
import Login.Register;
//import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
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

        get("/", (req, res) -> {res.redirect(Constants.LOGIN); return null;});


        get(Constants.LOGIN, (request, response) -> View.render(request, new HashMap<>(),Constants.LOGIN_TEMPLATE));

        post(Constants.START, LoginController.loginIfRegistered);
        get(Constants.CATALOG, ShowBooks.viewBooks);
        get(Constants.CATALOG+"/:name", ShowBooks.viewSpecificBooks);
        get(Constants.REGISTER,  Register.giveInformation);
        post(Constants.REGISTER, Register.register);
        get(Constants.START, LoginController.start);
        get(Constants.LOGOUT, LoginController.logout);
        get(Constants.UPLOADBOOK, UploadController.giveInformation);
        post(Constants.UPLOADBOOK, UploadController.upload);

    }

}
