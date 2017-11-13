package util;
import org.apache.velocity.*;
import org.apache.velocity.app.VelocityEngine;
import spark.ModelAndView;
import spark.Request;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

import java.util.Map;

public class View {

    private static VelocityTemplateEngine MyVelocityEngine() {
        VelocityEngine configuredEngine = new VelocityEngine();
        configuredEngine.setProperty("input.encoding", "UTF-8");
        configuredEngine.setProperty("output.encoding", "UTF-8");
        configuredEngine.setProperty("runtime.references.strict", true);
        configuredEngine.setProperty("resource.loader", "class");
        configuredEngine.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        configuredEngine.init();
        return new VelocityTemplateEngine(configuredEngine);
    }


    public static String render(Request request, Map<String, Object> model, String templatePath) {
//        model.put("msg", new MessageBundle(getSessionLocale(request)));
//        model.put("currentUser", getSessionCurrentUser(request));
//        model.put("WebPath", Path.Web.class); // Access application URLs from templates;
          return MyVelocityEngine().render(new ModelAndView(model, templatePath));
    }


}
