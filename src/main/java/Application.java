import spark.ModelAndView;
import util.Constants;

import java.util.HashMap;

import static spark.Spark.*;
import org.apache.velocity.*;
import spark.template.velocity.*;
import util.View;

public class Application {
    public static void main(String[] args) {

//        staticFiles.location("/Templates");

        port(8000);
        get("/", (req, res) -> "Hello world");
        get(Constants.INDEX, (request, response) -> {
            return View.render(request, new HashMap<>(),Constants.TEMPLATE);});
    }

}
