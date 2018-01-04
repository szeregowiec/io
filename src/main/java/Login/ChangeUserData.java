package Login;

import Base.Database;
import DataSchema.ReadersEntity;
import Main.Application;
import spark.Route;
import util.Constants;
import util.Password;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class ChangeUserData {


    public static Route ChangeUserDataPost = (request, response) -> {

        if(LoginController.ifUserIsNotLogged(request,response)) {
            response.redirect(Constants.LOGIN);
            return "";
        }
        Map<String,Object> model = new HashMap<>();

        List readers = Main.Application.database.getSession().createQuery("FROM ReadersEntity WHERE email = :email").setParameter("email", request.session().attribute("currentUser")).list();
        ReadersEntity reader = ((ReadersEntity) readers.get(0));


        if(request.queryParams().contains("ChangeLoginNew") && !request.queryParams("ChangeLoginNew").equals(reader.getLogin())){
            reader.setLogin(request.queryParams("ChangeLoginNew"));
            Application.database.getSession().update(reader);
            Database.myUpdate();

            request.session().removeAttribute("login");
            request.session().attribute("login", request.queryParams("ChangeLoginNew"));

            model.put("login", request.session().attribute("login"));
            model.put("email", reader.getEmail());
            model.put("password", "");
            model.put("name", reader.getName());
            model.put("surname", reader.getSurname());
            model.put("birth_date", reader.getBirthDate());
            model.put("address", reader.getAddress());
            model.put("postal_code", reader.getPostalCode());
            model.put("city", reader.getCity());
            model.put("phone", reader.getPhone());

            return util.View.render(request, model, Constants.CHANGEUSERDATA_TEMPLATE);
        }

        if(request.queryParams().contains("ChangeAddressNew")&& !request.queryParams("ChangeAddressNew").equals(reader.getAddress())){

            reader.setAddress(request.queryParams("ChangeAddressNew"));
            Application.database.getSession().update(reader);
            Database.myUpdate();

            model.put("login", request.session().attribute("login"));
            model.put("email", reader.getEmail());
            model.put("password", "");
            model.put("name", reader.getName());
            model.put("surname", reader.getSurname());
            model.put("birth_date", reader.getBirthDate());
            model.put("address", reader.getAddress());
            model.put("postal_code", reader.getPostalCode());
            model.put("city", reader.getCity());
            model.put("phone", reader.getPhone());


            return util.View.render(request, model, Constants.CHANGEUSERDATA_TEMPLATE);
        }

        if(request.queryParams().contains("ChangePostalCodeNew")&& !request.queryParams("ChangePostalCodeNew").equals(reader.getPostalCode())){

            reader.setPostalCode(request.queryParams("ChangePostalCodeNew"));
            Application.database.getSession().update(reader);
            Database.myUpdate();

            model.put("login", request.session().attribute("login"));
            model.put("email", reader.getEmail());
            model.put("password", "");
            model.put("name", reader.getName());
            model.put("surname", reader.getSurname());
            model.put("birth_date", reader.getBirthDate());
            model.put("address", reader.getAddress());
            model.put("postal_code", reader.getPostalCode());
            model.put("city", reader.getCity());
            model.put("phone", reader.getPhone());

            return util.View.render(request, model, Constants.CHANGEUSERDATA_TEMPLATE);
        }


        if(request.queryParams().contains("ChangeCityNew")&& !request.queryParams("ChangeCityNew").equals(reader.getCity())){

            reader.setCity(request.queryParams("ChangeCityNew"));
            Application.database.getSession().update(reader);
            Database.myUpdate();

            model.put("login", request.session().attribute("login"));
            model.put("email", reader.getEmail());
            model.put("password", "");
            model.put("name", reader.getName());
            model.put("surname", reader.getSurname());
            model.put("birth_date", reader.getBirthDate());
            model.put("address", reader.getAddress());
            model.put("postal_code", reader.getPostalCode());
            model.put("city", reader.getCity());
            model.put("phone", reader.getPhone());

            return util.View.render(request, model, Constants.CHANGEUSERDATA_TEMPLATE);
        }


        if(request.queryParams().contains("ChangePhoneNew")&& !request.queryParams("ChangePhoneNew").equals(reader.getPhone())){

            reader.setPhone(request.queryParams("ChangePhoneNew"));
            Application.database.getSession().update(reader);
            Database.myUpdate();

            model.put("login", request.session().attribute("login"));
            model.put("email", reader.getEmail());
            model.put("password", "");
            model.put("name", reader.getName());
            model.put("surname", reader.getSurname());
            model.put("birth_date", reader.getBirthDate());
            model.put("address", reader.getAddress());
            model.put("postal_code", reader.getPostalCode());
            model.put("city", reader.getCity());
            model.put("phone", reader.getPhone());

            return util.View.render(request, model, Constants.CHANGEUSERDATA_TEMPLATE);
        }

        if (request.queryParams().contains("ChangePasswordOld") && reader.getPassword().equals(Password.getHashedPassword(request.queryParams("ChangePasswordOld")))){
            reader.setPassword(Password.getHashedPassword(request.queryParams("ChangePasswordNew")));


            Application.database.getSession().update(reader);
            Database.myUpdate();

            model.put("login", request.session().attribute("login"));
            model.put("email", reader.getEmail());

            model.put("password", "Hasło zostało zmienione");

            model.put("name", reader.getName());
            model.put("surname", reader.getSurname());
            model.put("birth_date", reader.getBirthDate());
            model.put("address", reader.getAddress());
            model.put("postal_code", reader.getPostalCode());
            model.put("city", reader.getCity());
            model.put("phone", reader.getPhone());


            return util.View.render(request, model, Constants.CHANGEUSERDATA_TEMPLATE);

        } else{

            model.put("login", request.session().attribute("login"));
            model.put("email", reader.getEmail());

            model.put("password", "Hasło niepoprawne");

            model.put("name", reader.getName());
            model.put("surname", reader.getSurname());
            model.put("birth_date", reader.getBirthDate());
            model.put("address", reader.getAddress());
            model.put("postal_code", reader.getPostalCode());
            model.put("city", reader.getCity());
            model.put("phone", reader.getPhone());

            return util.View.render(request, model, Constants.CHANGEUSERDATA_TEMPLATE);
        }


    };


    public static Route changeUserData = (request, response) -> {

        if(LoginController.ifUserIsNotLogged(request,response)){
            response.redirect(Constants.LOGIN);
            return "";
        }
        Map<String,Object> model = new HashMap<>();
        if(LoginController.ifItIsNotReader(request,response)){
            response.redirect(Constants.SETTINGS);
            /*model.put("login",request.session().attribute("login    "));
            if(request.session().attributes().contains("alreadyExist")){
                model.put("bookAlreadyExists", true);
                request.session().removeAttribute("alreadyExist");
            } else if(request.session().attributes().contains("wrongURL")) {
                model.put("wrongURL", true);
                request.session().removeAttribute("wrongURL");
            } else model.put("bookAlreadyExists", false);*/
            return "";

            //return util.View.render(request, model, Constants.UPLOADBOOK_TEMPLATE);
        }else{
            List readers = Main.Application.database.getSession().createQuery("FROM ReadersEntity WHERE email = :email").setParameter("email", request.session().attribute("currentUser")).list();
            ReadersEntity reader = ((ReadersEntity) readers.get(0));

            model.put("login", request.session().attribute("login"));
            model.put("email", reader.getEmail());
            model.put("password", "");
            model.put("name", reader.getName());
            model.put("surname", reader.getSurname());
            model.put("birth_date", reader.getBirthDate());
            model.put("address", reader.getAddress());
            model.put("postal_code", reader.getPostalCode());
            model.put("city", reader.getCity());
            model.put("phone", reader.getPhone());

            return util.View.render(request, model, Constants.CHANGEUSERDATA_TEMPLATE);
        }



        //if(request.queryParams().contains("ChangePasswordOld") && request.queryParams().contains("ChangePasswordNew")){



    };



}
