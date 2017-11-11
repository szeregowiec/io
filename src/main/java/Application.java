import static spark.Spark.*;

public class Application {
    public static void main(String[] args) {
        port(8000);
        get("/", (req, res) -> "Hello world");
    }

}
