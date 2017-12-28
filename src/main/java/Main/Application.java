package Main;

import Base.Database;
import Book.EditController;
import Book.LoanController;
import Book.ShowBooks;
import Book.UploadController;
import Login.ChangeUserData;
import Login.LoginController;
import Login.Register;
//import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import Login.UserBookInfo;
import util.Constants;

import java.util.HashMap;

import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;

import util.View;

public class Application {

    public static Database database;

    public static void main(String[] args) {
        staticFiles.externalLocation(System.getProperty("user.dir") + "/src/main/resources/Views");
        port(8000);
        init();
        staticFiles.expireTime(600L);
        database = new Database();


       // before(Constants.CATALOG,(request, response) -> { System.out.println("test");});

        get("/", (req, res) -> {res.redirect(Constants.LOGIN); return null;});


        get(Constants.LOGIN, (request, response) -> View.render(request, new HashMap<>(),Constants.LOGIN_TEMPLATE));

        post(Constants.START, LoginController.loginIfRegistered);
        get(Constants.CATALOG, ShowBooks.viewBooks);
        get(Constants.CATEGORY, ShowBooks.viewSpecificBooks);
        get(Constants.ONE_BOOK, ShowBooks.viewOneBook);
        post(Constants.ONE_BOOK, LoanController.loanBook);
        get(Constants.REGISTER,  Register.giveInformation);
        post(Constants.REGISTER, Register.register);
        get(Constants.START, LoginController.start);
        get(Constants.LOGOUT, LoginController.logout);
        get(Constants.SETTINGS, UploadController.giveInformation);
        post(Constants.SETTINGS, UploadController.upload);
        post(Constants.EDIT_BOOK, EditController.giveInformation);
        post(Constants.EDIT, EditController.editBook);
        post(Constants.DELETE_BOOK, EditController.deleteBook);
        get(Constants.RESERVED_BOOKS, UserBookInfo.reservedBooks);
        post(Constants.DELETE_RESERVED_BOOK, UserBookInfo.deleteReservedBook);
        get(Constants.BORROWED_BOOKS, UserBookInfo.borrowedBooks);
        post(Constants.PROLONG_BORROWED_BOOK, UserBookInfo.prolongBorrowedBook);
        get(Constants.PENALTIES, UserBookInfo.userPenalties);

        get(Constants.CHANGEUSERDATA, ChangeUserData.changeUserData);
        post(Constants.CHANGEUSERDATA, ChangeUserData.ChangeUserDataPost);

    }

}
