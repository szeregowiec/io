package Login;

import DataSchema.ReadersEntity;
import spark.Route;
import util.Constants;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static Main.Application.database;
import static spark.Spark.redirect;
import static util.Password.getHashedPassword;


public class Register {

    public static Route register = (request, response) -> {
        //if user(email) already exist
        List reader = database.getSession().createQuery("FROM ReadersEntity WHERE email = :email").setParameter("email", request.queryParams("inputEmail")).list();
        if(!reader.isEmpty()){
            request.session().attribute("alreadyExist",true);
            response.redirect(Constants.REGISTER);
            return "";
        }

        //making new user
        ReadersEntity newReader = new ReadersEntity();
        newReader.setEmail(request.queryParams("inputEmail"));
        newReader.setLogin(request.queryParams("inputLogin"));
        newReader.setPassword(getHashedPassword(request.queryParams("inputPassword")));
        newReader.setName(request.queryParams("inputName"));
        newReader.setSurname(request.queryParams("inputSurname"));
        newReader.setAddress(request.queryParams("inputAddress"));
        newReader.setPostalCode(request.queryParams("inputPostalCode"));
        newReader.setCity(request.queryParams("inputCity"));
        newReader.setPhone(request.queryParams("inputPhone"));
        newReader.setPenalty(0);


        /**
         * a to po to żeby dobrze znaleźć date urodzin ze stringa
         * lepiej by było to brać z oddzielnych pól, no ale cóż
         */
        try{
            int year=0, month=0, day=0;
            String dataGiven = request.queryParams("inputDateBirth");

            /*if(Pattern.matches("\\d\\d\\d\\d-\\d\\d-\\d\\d",dataGiven)){
                year = Integer.parseInt(dataGiven.substring(0,4));
                month = Integer.parseInt(dataGiven.substring(5,7));
                day = Integer.parseInt(dataGiven.substring(8,10));
                System.out.println(year+", "+month+", "+day);
            }else if(Pattern.matches("\\d\\d-\\d\\d-\\d\\d\\d\\d",dataGiven)){
                year = Integer.parseInt(dataGiven.substring(6,9));
                month = Integer.parseInt(dataGiven.substring(3,4));
                day = Integer.parseInt(dataGiven.substring(0,1));
            } else{
                System.out.println("Data nie pasuje do niczego");
                response.redirect(Constants.REGISTER);
            }*/

            //Date birthDate = new Date(year,month,day);
            Date birthDate = Date.valueOf(dataGiven);
            newReader.setBirthDate(birthDate);
        }catch(Exception e){
            response.redirect(Constants.REGISTER);
            System.out.println("Wyjebalo wyjatek");
        }
        int max_id = Integer.parseInt(database.getSession().createQuery("select max(idReader) from ReadersEntity ").list().get(0).toString());
        newReader.setIdReader(Integer.toString(max_id + 1));

        database.getSession().save("ReadersEntity", newReader);
        database.getSession().getTransaction().commit();

        response.redirect(Constants.LOGIN);
    return newReader.getIdReader()+"\n"+newReader.getName()+"\n"+newReader.getSurname()+"\n"+newReader.getPassword()+"\n"+newReader.getLogin();
    };


    public static Route giveInformation = (request, response) -> {
        Map<String,Object> model = new HashMap<>();

        if(request.session().attributes().contains("alreadyExist")){
        model.put("readerAlreadyExist", true);
        request.session().removeAttribute("alreadyExist");
        } else {model.put("readerAlreadyExist", false);}

        model.put("StronaDoLogowania",Constants.LOGIN);
        return util.View.render(request, model, Constants.REGISTER_TEMPLATE);
    };

}
