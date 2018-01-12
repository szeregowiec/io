package Admin.BookController;

import DataSchema.BooksEntity;
import DataSchema.CopiesEntity;
import User.Login.LoginController;

import spark.Request;
import spark.Response;
import spark.Route;
import util.Constants;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import DataSchema.Base.Database;


import static Main.Application.database;

/**
 * Klasa kontrolująca dodawanie nowych ksiązek do biblioteki
 */
public class UploadController {


    /**
     *
     * @param param nazwa parametru otrzymanego ze spark poprzez Request w którym znajduje się okładka
     * @param path scieżka do zapisu okładki
     * @param request parametr z którego otrzymujemy informacje przekazane z velocity
     * @param response parametr do którego wysyłamy informacje zwrotną
     */
    static void createCover(String param, String path, Request request, Response response) {
        try {
            String imgURL = request.queryParams(param);
            URL url = new URL(imgURL);
            InputStream in = new BufferedInputStream(url.openStream());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int n;// = 0;
            while (-1 != (n = in.read(buf))) {
                out.write(buf, 0, n);
            }
            out.close();
            in.close();
            byte[] response1 = out.toByteArray();
            File file = new File("src\\main\\resources\\Views\\Covers\\" + path + ".jpg");
            FileOutputStream fos = new FileOutputStream(file,false);
            fos.write(response1);
            fos.flush();
            fos.close();
        }catch(Exception e){
            request.session().attribute("wrongURL",true);
            response.redirect(Constants.SETTINGS);
            //return "";
        }
    }

    /**
     * Dodaje do bazy nową książkę wraz z okładką i jej kopiami
     */
    public static Route upload = (request, response) -> {

        List book = database.getSession().createQuery("FROM BooksEntity WHERE isbn = :isbn").setParameter("isbn", request.queryParams("inputIsbn")).list();
        if(!book.isEmpty()){
            request.session().attribute("alreadyExist",true);
            response.redirect(Constants.UPLOADBOOK);
            return "";
        }
        try{
            createCover("inputCover",request.queryParams("inputIsbn"),request,response);
        }catch(Exception e){
            request.session().attribute("wrongURL",true);
            response.redirect(Constants.SETTINGS);
            return "";
        }

        BooksEntity newBook = new BooksEntity();
        newBook.setTitle(request.queryParams("inputTitle"));
        newBook.setAuthors(request.queryParams("inputAuthor"));
        newBook.setCategory(request.queryParams("inputKategory"));
        newBook.setDescription(request.queryParams("inputDescription"));
        newBook.setIsbn(request.queryParams("inputIsbn"));
        newBook.setLanguage(request.queryParams("inputLanguage"));
        newBook.setPages(request.queryParams("inputPages"));
        newBook.setPublishingHouse(request.queryParams("inputPublishingHouse"));
        newBook.setPublishYear(request.queryParams("inputDate"));
        newBook.setPublishPlace(request.queryParams("inputPlace"));
        newBook.setCover(request.queryParams("inputIsbn")+".jpg");
        Byte tmp = 1;
        newBook.setVisibility(tmp);



        database.getSession().save("BooksEntity", newBook);

        // kopie
        CopiesEntity newCopie;
        int max_id;
        for(int i = 0; i < Integer.parseInt(request.queryParams("inputAmount")); i++){
            newCopie = new CopiesEntity();
            newCopie.setIsbn(request.queryParams("inputIsbn"));
            try{
                max_id = Integer.parseInt(database.getSession().createQuery("select max(idBook) from CopiesEntity ").list().get(0).toString());
            }catch (NullPointerException e) {
                max_id = 0;
            }
            //System.out.println(max_id);
            newCopie.setIdBook(max_id + 1);
            database.getSession().save("CopiesEntity", newCopie);
        }
        Database.myUpdate();

        response.redirect(Constants.SETTINGS);
        return "Success";

    };
    /**
     * Wyświetla stronę/formularz dodawania książki
     */
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
        if(request.session().attributes().contains("alreadyExist")){
            model.put("bookAlreadyExists", true);
            request.session().removeAttribute("alreadyExist");
        } else if(request.session().attributes().contains("wrongURL")) {
            model.put("wrongURL", true);
            request.session().removeAttribute("wrongURL");
        } else model.put("bookAlreadyExists", false);

        return util.View.render(request, model, Constants.UPLOADBOOK_TEMPLATE);
    };
}