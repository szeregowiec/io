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
    public static void main(String[] args) {

        staticFiles.location("/Views");

        port(4567);
        get("/", (req, res) -> "Hello world");
        get(Constants.INDEX, (request, response) -> {
            return View.render(request, new HashMap<>(),Constants.TEMPLATE);
        });

        //System.out.println(LoginController.getId() + "CHciałem bytch");



        //creating configuration object
        Configuration cfg=new Configuration();
        cfg.configure("hibernate.cfg.xml");//populates the data of the configuration file

        //creating seession factory object
        SessionFactory factory=cfg.buildSessionFactory();

        //creating session object
        Session session=factory.openSession();

        //creating transaction object
        Transaction t=session.beginTransaction();

//        ReadersEntity re = session.get(ReadersEntity.class, new Integer[10]);
//        System.out.println(re.getIdReader() + "coś, ktoś moś");
        //System.out.println("Chciałem bytch");
        List readers = session.createQuery("FROM ReadersEntity").list();
        for (Iterator iterator = readers.iterator(); iterator.hasNext();){
            ReadersEntity readersIteration = (ReadersEntity) iterator.next();
            System.out.print("First Name: " + readersIteration.getLogin());
            System.out.print("  Last Name: " + readersIteration.getEmail());
            System.out.println("  Salary: " + readersIteration.getIdReader());
        }



    }



}
