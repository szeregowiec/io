package Admin.BookController;

import DataSchema.Base.Database;
import DataSchema.BooksEntity;
import DataSchema.CopiesEntity;
import DataSchema.ReservedEntity;
import User.Login.LoginController;
import Main.Application;
import MyRunnable.MailSenderRunnable;
import spark.Route;
import util.Constants;

import java.io.IOException;
import java.nio.file.*;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static Admin.BookController.UploadController.createCover;
import static Main.Application.database;
import static MyRunnable.MailSender.*;

public class EditController {

    public static Route deleteBook = (request, response) -> {
        if(LoginController.ifUserIsNotLogged(request,response)){
            response.redirect(Constants.LOGIN);
            return "";
        }
        if(!LoginController.ifItIsNotReader(request,response)){
            response.redirect(Constants.START);
            return "";
        }
       // List books = Application.database.getSession().createQuery("from BooksEntity where isbn = :isbn").setParameter("isbn",request.params(":isbn")).list();
        BooksEntity book = (BooksEntity)(Application.database.getSession().createQuery("from BooksEntity where isbn = :isbn").setParameter("isbn",request.params(":isbn")).list()).get(0);
        Application.database.getSession().createQuery("delete BooksEntity where isbn = :isbn").setParameter("isbn",request.params(":isbn")).executeUpdate();
        Application.database.getSession().createQuery("delete CopiesEntity where isbn = :isbn").setParameter("isbn",request.params(":isbn")).executeUpdate();
        List<Integer> idReaderList = database.getSession().createQuery("select idReader from ReservedEntity where isbn = :isbn").setParameter("isbn", book.getIsbn()).list();
        Application.database.getSession().createQuery("delete ReservedEntity where isbn = :isbn").setParameter("isbn", request.params(":isbn")).executeUpdate();

        String[] emailList = new String[idReaderList.size()];
        int i = 0;
        for(int idReader: idReaderList){
            //List emailTmp = database.getSession().createQuery("select email from ReadersEntity where idReader = :idReader").setParameter("idReader", idReader).list();
            String email = (String)(database.getSession().createQuery("select email from ReadersEntity where idReader = :idReader").setParameter("idReader", idReader).list()).get(0);
            emailList[i] = email;
            i++;
        }
        if(emailList.length !=0) sendCancelNotification(book,emailList);
        //Application.database.getSession().createQuery("delete ReservedEntity where isbn = :isbn").setParameter("isbn", request.params(":isbn")).executeUpdate();
        Database.myUpdate();
        Path path = FileSystems.getDefault().getPath("src\\main\\resources\\Views\\Covers\\", request.params(":isbn")+".jpg");
        deleteCover(path);
        response.redirect(Constants.CATALOG);
        return "";
    };

    public static Route editBook = (request, response) -> {
        if(LoginController.ifUserIsNotLogged(request,response)){
            response.redirect(Constants.LOGIN);
            return "";
        }
        if(!LoginController.ifItIsNotReader(request,response)){
            response.redirect(Constants.START);
            return "";
        }
        Map<String,Object> model = new HashMap<>();

        //List books = database.getSession().createQuery("FROM BooksEntity WHERE isbn = :isbn").setParameter("isbn",request.params(":isbn")).list();
        BooksEntity book = (BooksEntity)(database.getSession().createQuery("FROM BooksEntity WHERE isbn = :isbn").setParameter("isbn",request.params(":isbn")).list()).get(0);

        if(request.queryParams().contains("ChangeTitleNew") && !request.queryParams("ChangeTitleNew").equals(book.getTitle())){
            book.setTitle(request.queryParams("ChangeTitleNew"));
            Application.database.getSession().update(book);
            Database.myUpdate();

            model.put("login", request.session().attribute("login"));
            model.put("book", book);
        }
        if(request.queryParams().contains("ChangeAuthorsNew") && !request.queryParams("ChangeAuthorsNew").equals(book.getTitle())){
            book.setAuthors(request.queryParams("ChangeAuthorsNew"));
            Application.database.getSession().update(book);
            Database.myUpdate();
            model.put("login", request.session().attribute("login"));
            model.put("book", book);
        }
        if(request.queryParams().contains("ChangeCategoryNew") && !request.queryParams("ChangeCategoryNew").equals(book.getTitle())){
            book.setCategory(request.queryParams("ChangeCategoryNew"));
            Application.database.getSession().update(book);
            Database.myUpdate();
            model.put("login", request.session().attribute("login"));
            model.put("book", book);
        }
        if(request.queryParams().contains("ChangePublishingHouseNew") && !request.queryParams("ChangePublishingHouseNew").equals(book.getTitle())){
            book.setPublishingHouse(request.queryParams("ChangePublishingHouseNew"));
            Application.database.getSession().update(book);
            Database.myUpdate();
            model.put("login", request.session().attribute("login"));
            model.put("book", book);
        }
        if(request.queryParams().contains("ChangePublishYearNew") && !request.queryParams("ChangePublishYearNew").equals(book.getTitle())){
            book.setPublishYear(request.queryParams("ChangePublishYearNew"));
            Application.database.getSession().update(book);
            Database.myUpdate();
            model.put("login", request.session().attribute("login"));
            model.put("book", book);
        }
        if(request.queryParams().contains("ChangePublishPlaceNew") && !request.queryParams("ChangePublishPlaceNew").equals(book.getTitle())){
            book.setPublishPlace(request.queryParams("ChangePublishPlaceNew"));
            Application.database.getSession().update(book);
            Database.myUpdate();
            model.put("login", request.session().attribute("login"));
            model.put("book", book);
        }
        if(request.queryParams().contains("ChangePagesNew") && !request.queryParams("ChangePagesNew").equals(book.getTitle())){
            book.setPages(request.queryParams("ChangePagesNew"));
            Application.database.getSession().update(book);
            Database.myUpdate();
            model.put("login", request.session().attribute("login"));
            model.put("book", book);
        }
        if(request.queryParams().contains("ChangeDescriptionNew") && !request.queryParams("ChangeDescriptionNew").equals(book.getTitle())){
            book.setDescription(request.queryParams("ChangeDescriptionNew"));
            Application.database.getSession().update(book);
            Database.myUpdate();
            model.put("login", request.session().attribute("login"));
            model.put("book", book);
        }
        if(request.queryParams().contains("ChangeLanguageNew") && !request.queryParams("ChangeLanguageNew").equals(book.getTitle())){
            book.setLanguage(request.queryParams("ChangeLanguageNew"));
            Application.database.getSession().update(book);
            Database.myUpdate();
            model.put("login", request.session().attribute("login"));
            model.put("book", book);
        }
        if(request.queryParams().contains("ChangeAmountNew") && !request.queryParams("ChangeAmountNew").equals(book.getTitle())){
            int amount = Integer.parseInt(request.queryParams("ChangeAmountNew"));
            CopiesEntity newCopie;
            int max_id;
            List reserved = database.getSession().createQuery("from ReservedEntity where expireDate is null and isbn = :isbn").setParameter("isbn", book.getIsbn()).list();
            ReservedEntity reservedBook = null;
            String[] emailList;
            if(reserved.size() < amount) emailList = new String[reserved.size()];
            else emailList = new String[amount];

            int j = 0;
            for(int i = 0; i < amount; i++){
                newCopie = new CopiesEntity();
                newCopie.setIsbn(book.getIsbn());
                try{
                    max_id = Integer.parseInt(database.getSession().createQuery("select max(idBook) from CopiesEntity").list().get(0).toString());
                }catch (NullPointerException e) {
                    max_id = 0;
                }
                newCopie.setIdBook(max_id + 1);
                database.getSession().save("CopiesEntity", newCopie);
                if(!reserved.isEmpty()){
                    reservedBook = (ReservedEntity)reserved.get(0);
                    reserved.remove(0);
                    reservedBook.setExpireDate(new Date(new java.util.Date().getTime()+ 604800000));
                    Application.database.getSession().update(reservedBook);
                    //List<String> emailTmp = database.getSession().createQuery("select email from ReadersEntity where idReader = :idReader").setParameter("idReader", reservedBook.getIdReader()).list();
                    //String email = (String)(database.getSession().createQuery("select email from ReadersEntity where idReader = :idReader").setParameter("idReader", reservedBook.getIdReader()).list()).get(0);
                    emailList[j++] = (String)(database.getSession().createQuery("select email from ReadersEntity where idReader = :idReader").setParameter("idReader", reservedBook.getIdReader()).list()).get(0);


                }

            }
            if(emailList.length != 0)/* sendAvailabilityNotification(reservedBook,book,emailList); */   (new Thread(new MailSenderRunnable(1,reservedBook,book,emailList))).start();
            Database.myUpdate();
            model.put("login", request.session().attribute("login"));
            model.put("book", book);

        }
        if(request.queryParams().contains("ChangeCoverNew") && !request.queryParams("ChangeCoverNew").equals(book.getTitle())){
            Path path = FileSystems.getDefault().getPath("src\\main\\resources\\Views\\Covers\\", request.params(":isbn")+".jpg");
            deleteCover(path);
            createCover("ChangeCoverNew", request.params(":isbn"),request,response);
            model.put("login", request.session().attribute("login"));
            model.put("book", book);
        }

        /*int copiesAmount;
        //Database.myUpdate();
        try{
            copiesAmount = Integer.parseInt(database.getSession().createQuery("select count(isbn) from CopiesEntity where isbn = :isbn").setParameter("isbn", request.params(":isbn")).list().get(0).toString());
        }catch(NullPointerException e){
            copiesAmount = 0;
        }
        model.put("amount", copiesAmount);
        request.session().attribute("amount", copiesAmount);*/
        List ce = database.getSession().createQuery("select idBook from CopiesEntity where isbn = :isbn").setParameter("isbn",book.getIsbn()).list();
        List be = database.getSession().createQuery("select be.idBook from BorrowedEntity as be join CopiesEntity as ce on be.idBook = ce.idBook where ce.isbn = :isbn").setParameter("isbn",book.getIsbn()).list();
        ce.removeAll(be);
        model.put("copiesBook", ce);
        request.session().attribute("copiesBook", ce);
        return util.View.render(request, model, Constants.EDIT_BOOK_TEMPLATE);

    };

    public static Route deleteCopy = (request, response) -> {
        if(LoginController.ifUserIsNotLogged(request,response)){
            response.redirect(Constants.LOGIN);
            return "";
        }
        if(!LoginController.ifItIsNotReader(request,response)){
            response.redirect(Constants.START);
            return "";
        }
        CopiesEntity copy = (CopiesEntity)(database.getSession().createQuery("from CopiesEntity where idBook = :idBook").setParameter("idBook", Integer.parseInt(request.params(":id"))).list()).get(0);
        List<CopiesEntity> copies = database.getSession().createQuery("from CopiesEntity where isbn = :isbn").setParameter("isbn", copy.getIsbn()).list();
        List reservedBooks = database.getSession().createQuery("from ReservedEntity where expireDate is not null and isbn = :isbn").setParameter("isbn", copy.getIsbn()).list();
        if(!reservedBooks.isEmpty() && copies.size() == reservedBooks.size()){
            ReservedEntity reservedBook = (ReservedEntity)reservedBooks.get(reservedBooks.size()-1);
            reservedBook.setExpireDate(null);
            Application.database.getSession().update(reservedBook);
            //List<String> emails = database.getSession().createQuery("select email from ReadersEntity where idReader = :idReader").setParameter("idReader", reservedBook.getIdReader()).list();
            //String email = (String)(database.getSession().createQuery("select email from ReadersEntity where idReader = :idReader").setParameter("idReader", reservedBook.getIdReader()).list()).get(0);
            String[] emailList = {(String)(database.getSession().createQuery("select email from ReadersEntity where idReader = :idReader").setParameter("idReader", reservedBook.getIdReader()).list()).get(0)};
            //List books = database.getSession().createQuery("from BooksEntity where isbn = :isbn").setParameter("isbn", copy.getIsbn()).list();
            BooksEntity book = (BooksEntity)(database.getSession().createQuery("from BooksEntity where isbn = :isbn").setParameter("isbn", copy.getIsbn()).list()).get(0);
            //sendNotAvailabilityNotification(book,emailList);
            (new Thread(new MailSenderRunnable(3,null,book,emailList))).start();
        }

        database.getSession().createQuery("delete CopiesEntity where idBook = :idBook").setParameter("idBook", Integer.parseInt(request.params(":id"))).executeUpdate();
        Database.myUpdate();
        response.redirect("/editbook/"+ copy.getIsbn());
        return "";

    };

    public static Route hideShowBook = (request, response) -> {
        if(LoginController.ifUserIsNotLogged(request,response)){
            response.redirect(Constants.LOGIN);
            return "";
        }
        if(!LoginController.ifItIsNotReader(request,response)){
            response.redirect(Constants.START);
            return "";
        }

        //Map<String,Object> model = new HashMap<>();

        //List<BooksEntity> books = database.getSession().createQuery("FROM BooksEntity WHERE isbn = :isbn").setParameter("isbn",request.params(":isbn")).list();
        BooksEntity book = (BooksEntity)(database.getSession().createQuery("FROM BooksEntity WHERE isbn = :isbn").setParameter("isbn",request.params(":isbn")).list()).get(0);
        Byte tmp = 0;
        if(book.getVisibility() == 1) {
            book.setVisibility(tmp);
            List<Integer> idReaderList = database.getSession().createQuery("select idReader from ReservedEntity where isbn = :isbn").setParameter("isbn", book.getIsbn()).list();
            Application.database.getSession().createQuery("delete ReservedEntity where isbn = :isbn").setParameter("isbn",book.getIsbn()).executeUpdate();

            String[] emailList = new String[idReaderList.size()];
            int i = 0;
            for(int idReader: idReaderList){
               // List<String> emailTmp = database.getSession().createQuery("select email from ReadersEntity where idReader = :idReader").setParameter("idReader", idReader).list();
                //String email = (String)(database.getSession().createQuery("select email from ReadersEntity where idReader = :idReader").setParameter("idReader", idReader).list()).get(0);
                emailList[i] = (String)(database.getSession().createQuery("select email from ReadersEntity where idReader = :idReader").setParameter("idReader", idReader).list()).get(0);
                i++;
            }
            //String subject = "Anulowana rezerwacja";
            //String body = "Z powodu wycofania książki " + book.getTitle() + " autorstwa " + book.getAuthors() + " pańska rezerwacja została anulowana.\nZa utrudnienia przepraszamy.";
            if(emailList.length !=0) /*sendCancelNotification(book,emailList);*/(new Thread(new MailSenderRunnable(2,null,book,emailList))).start(); //MyRunnable.MailSender.sendFromGMail(emailList,subject,body);
        }
        else {
            tmp = 1;
            book.setVisibility(tmp);
        }
        Application.database.getSession().update(book);
        Database.myUpdate();
        response.redirect("/book/"+ book.getIsbn());
        return "";
    };

    public static Route giveInformation = (request, response) -> {
        if(LoginController.ifUserIsNotLogged(request,response)){
            response.redirect(Constants.LOGIN);
            return "";
        }
        if(!LoginController.ifItIsNotReader(request,response)){
            response.redirect(Constants.START);
            return "";
        }
        Map<String,Object> model = new HashMap<>();
        model.put("login",request.session().attribute("login"));
        //List<BooksEntity> books = database.getSession().createQuery("FROM BooksEntity WHERE isbn = :isbn").setParameter("isbn",request.params(":isbn")).list();
        BooksEntity book = (BooksEntity)(database.getSession().createQuery("FROM BooksEntity WHERE isbn = :isbn").setParameter("isbn",request.params(":isbn")).list()).get(0);
        model.put("book", book);
        request.session().attribute("book",book);
        //int copiesAmount;
        //Database.myUpdate();
        /*try{
            copiesAmount = Integer.parseInt(database.getSession().createQuery("select count(isbn) from CopiesEntity where isbn = :isbn").setParameter("isbn", request.params(":isbn")).list().get(0).toString());
        }catch(NullPointerException e){
            copiesAmount = 0;
        }
        model.put("amount", copiesAmount);
        request.session().attribute("amount", copiesAmount);*/
        List ce = database.getSession().createQuery("select idBook from CopiesEntity where isbn = :isbn").setParameter("isbn",book.getIsbn()).list();
        List be = database.getSession().createQuery("select be.idBook from BorrowedEntity as be join CopiesEntity as ce on be.idBook = ce.idBook where ce.isbn = :isbn").setParameter("isbn",book.getIsbn()).list();
        ce.removeAll(be);
        model.put("copiesBook", ce);
        request.session().attribute("copiesBook", ce);
        return util.View.render(request, model, Constants.EDIT_BOOK_TEMPLATE);
    };

    private static void deleteCover(Path path) {
        //Path path = FileSystems.getDefault().getPath("src\\main\\resources\\Views\\Covers\\", request.queryParams("inputIsbn")+".jpg");
        try {
            Files.deleteIfExists(path);
        } catch (NoSuchFileException x) {
            System.err.format("%s: no such" + " file or directory%n", path);
        } catch (DirectoryNotEmptyException x) {
            System.err.format("%s not empty%n", path);
        } catch (IOException x) {
            // File permission problems are caught here.
            x.printStackTrace();
        }
    }



}
