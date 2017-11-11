package Login;
import DataSchema.ReadersEntity;


public class LoginController {

    public static String getId(){
        return new ReadersEntity().getIdReader();


    }


}
