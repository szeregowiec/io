package Book;

import DataSchema.BooksEntity;
import spark.Route;
import util.Constants;
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import Base.Database;


import static Main.Application.database;


public class UploadController {


    public static Route upload = (request, response) -> {

        List book = database.getSession().createQuery("FROM BooksEntity WHERE isbn = :isbn").setParameter("isbn", request.queryParams("inputIsbn")).list();
        if(!book.isEmpty()){
            request.session().attribute("alreadyExist",true);
            response.redirect(Constants.UPLOADBOOK);
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
        newBook.setPublishYear(request.queryParams("inputPages"));
        newBook.setPublishPlace(request.queryParams("inputPlace"));
        newBook.setCover(request.queryParams("inputIsbn")+".jpg");

        try {
            String imgURL = request.queryParams("inputCover");
            URL url = new URL(imgURL);
            InputStream in = new BufferedInputStream(url.openStream());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int n = 0;
            while (-1 != (n = in.read(buf))) {
                out.write(buf, 0, n);
            }
            out.close();
            in.close();
            byte[] response1 = out.toByteArray();
            File file = new File("src\\main\\resources\\Views\\Covers\\" + newBook.getIsbn() + ".jpg");
            FileOutputStream fos = new FileOutputStream(file,false);
            fos.write(response1);
            fos.flush();
            fos.close();
        }catch(Exception e){
            request.session().attribute("wrongURL",true);
            response.redirect(Constants.UPLOADBOOK);
            return "";
        }

        database.getSession().save("BooksEntity", newBook);
        Database.myUpdate();

        response.redirect(Constants.UPLOADBOOK);
        return "Success";

    };

    public static Route giveInformation = (request, response) -> {

        Map<String,Object> model = new HashMap<>();
        model.put("login",request.session().attribute("login    "));
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
