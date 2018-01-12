package Main;

import Admin.AdminBookInfo;
import User.UserBookInfo;
import DataSchema.Base.Database;
import Admin.BookController.EditController;
import Admin.BookController.LoanController;
import Admin.BookController.ShowBooksController;
import Admin.BookController.UploadController;
import User.Edit.ChangeUserDataController;
import User.Login.LoginController;
import User.Register.RegisterController;
import util.Constants;
import MyRunnable.Daemon;
import java.util.HashMap;

import static spark.Spark.*;


import util.View;

public class Application {

    public static Database database;

    public static void main(String[] args) {
        staticFiles.externalLocation(System.getProperty("user.dir") + "/src/main/resources/Views");
        port(8000);
        init();
        staticFiles.expireTime(600L);
        database = new Database();

        (new Thread(new Daemon())).start();


        get("/", (req, res) -> {res.redirect(Constants.LOGIN); return null;});


        get(Constants.LOGIN, (request, response) -> {
            if(!LoginController.ifUserIsNotLogged(request, response)){
                response.redirect(Constants.START);
                return "";
            }
            return View.render(request, new HashMap<>(),Constants.LOGIN_TEMPLATE);
        });
        before(Constants.CONFIRM_RETURNING, LoanController.setNewBorrowed);
        post(Constants.START, LoginController.loginIfRegistered);
        get(Constants.CATALOG, ShowBooksController.viewBooks);
        get(Constants.CATEGORY, ShowBooksController.viewSpecificBooks);
        get(Constants.ONE_BOOK, ShowBooksController.viewOneBook);
        post(Constants.ONE_BOOK, LoanController.loanBook);
        get(Constants.REGISTER,  RegisterController.giveInformation);
        post(Constants.REGISTER, RegisterController.register);
        get(Constants.START, LoginController.start);
        get(Constants.LOGOUT, LoginController.logout);
        get(Constants.SETTINGS, UploadController.giveInformation);
        post(Constants.SETTINGS, UploadController.upload);
        get(Constants.EDIT_BOOK, EditController.giveInformation);
        post(Constants.EDIT_BOOK, EditController.giveInformation);
        post(Constants.EDIT, EditController.editBook);
        post(Constants.DELETE_BOOK, EditController.deleteBook);
        post(Constants.HIDE_BOOK, EditController.hideShowBook);
        post(Constants.DELETE_COPY, EditController.deleteCopy);
        get(Constants.RESERVED_BOOKS, UserBookInfo.reservedBooks);
        post(Constants.DELETE_RESERVED_BOOK, UserBookInfo.deleteReservedBook);
        get(Constants.BORROWED_BOOKS, UserBookInfo.borrowedBooks);
        post(Constants.PROLONG_BORROWED_BOOK, UserBookInfo.prolongBorrowedBook);
        get(Constants.PENALTIES, UserBookInfo.userPenalties);
        get(Constants.BORROWING, AdminBookInfo.borrowing);
        post(Constants.CONFIRM_BORROWING, AdminBookInfo.confirmBorrowing);
        get(Constants.RETURNING, AdminBookInfo.returning);
        post(Constants.CONFIRM_RETURNING, AdminBookInfo.confirmReturning);
        get(Constants.PAYMENT, AdminBookInfo.payments);
        post(Constants.CONFIRM_PAYMENT, AdminBookInfo.confirmPayment);
        get(Constants.HISTORY, UserBookInfo.userHistory);
        post(Constants.SEARCH, ShowBooksController.viewFoundBooks);

        get(Constants.CHANGEUSERDATA, ChangeUserDataController.changeUserData);
        post(Constants.CHANGEUSERDATA, ChangeUserDataController.ChangeUserDataPost);



    }

}
