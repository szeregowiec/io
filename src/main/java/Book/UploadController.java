package Book;

import DataSchema.BooksEntity;
import spark.Route;
import util.Constants;
import java.sql.Date;
import java.util.HashMap;
import java.util.regex.Pattern;


import static Main.Application.database;

public class UploadController {

    public static Route upload = (request, response) -> {
        BooksEntity newBook = new BooksEntity();
        newBook.setTitle(request.queryParams("inputTitle"));
        newBook.setAuthors(request.queryParams("inputAuthor"));
        newBook.setCategory(request.queryParams("inputKategory"));
        newBook.setDescription(request.queryParams("inputDescription"));
        newBook.setIsbn(request.queryParams("inputIsbn"));
        newBook.setLanguage(request.queryParams("inputLanguage"));
        newBook.setPages(request.queryParams("inputPages"));
        newBook.setPublishingHouse(request.queryParams("inputPublishingHouse"));
        newBook.setPublishYear(Integer.parseInt(request.queryParams("inputDate")));
        newBook.setPublishPlace(request.queryParams("inputPlace"));

        //nie ma okładki!!!!! name to inputCover tak żebyś nie musiał sprawdzać ;)


        database.getSession().save("BooksEntity", newBook);
        database.getSession().getTransaction().commit();
        response.redirect(Constants.UPLOADBOOK);
        return "Success";
    };

    public static Route giveInformation = (request, response) -> {
        return util.View.render(request, new HashMap<>(), Constants.UPLOADBOOK_TEMPLATE);
    };
}
