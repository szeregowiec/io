package Base;

import DataSchema.ReadersEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.Iterator;
import java.util.List;

public class Database {

    private Session session;
    private Transaction transaction;
    public Database() {
        //creating configuration object
        Configuration cfg=new Configuration();
        cfg.configure("hibernate.cfg.xml");//populates the data of the configuration file

        //creating session factory object
        SessionFactory factory=cfg.buildSessionFactory();

        //creating session object
        session = factory.openSession();

        //creating transaction object
        transaction = session.beginTransaction();

        //System.out.println("Chciałem bytch");
//        List readers = session.createQuery("FROM ReadersEntity WHERE idReader like 'a%'").list();
//        for (Iterator iterator = readers.iterator(); iterator.hasNext();){
//            ReadersEntity readersIteration = (ReadersEntity) iterator.next();
//            System.out.print("First Name: " + readersIteration.getLogin());
//            System.out.print("  Last Name: " + readersIteration.getEmail());
//            System.out.println("  Salary: " + readersIteration.getIdReader());
//        }
    }

    public Session getSession(){
        return session;
    }

}
