package Main;

import Base.Database;
import Book.ShowBooks;
import Book.UploadController;
import Login.ChangeUserData;
import Login.LoginController;
import Login.Register;
//import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import util.Constants;

import java.util.HashMap;

import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;

import util.View;

public class Application {

    public static Database database;

    public static void main(String[] args) {

        staticFiles.location("/Views");
        port(8000);
        init();
        staticFiles.expireTime(600L);
        enableDebugScreen();
        database = new Database();

        get("/", (req, res) -> {res.redirect(Constants.LOGIN); return null;});


        get(Constants.LOGIN, (request, response) -> View.render(request, new HashMap<>(),Constants.LOGIN_TEMPLATE));

        post(Constants.START, LoginController.loginIfRegistered);
        get(Constants.CATALOG, ShowBooks.viewBooks);
        get(Constants.CATEGORY, ShowBooks.viewSpecificBooks);
        get(Constants.REGISTER,  Register.giveInformation);
        post(Constants.REGISTER, Register.register);
        get(Constants.START, LoginController.start);
        get(Constants.LOGOUT, LoginController.logout);
        get(Constants.UPLOADBOOK, UploadController.giveInformation);
        post(Constants.UPLOADBOOK, UploadController.upload);

        get(Constants.CHANGEUSERDATA, ChangeUserData.changeUserData);
        post(Constants.CHANGEUSERDATA, ChangeUserData.ChangeUserDataPost);

    }

}
