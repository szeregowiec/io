package util;

public class Constants {

    public static String LOGIN = "/login";
    public static String START = "/start";
    public static String REGISTER = "/register";
    public static String UPLOADBOOK = "/uploadbook";
    public static String LOGOUT = "/logout";
    public static String MAIN = "/main";
    public static String CATALOG = "/catalog";
    public static String CATEGORY = CATALOG+"/:category";
    public static String CHANGEUSERDATA = "/change";
    public static String ONE_BOOK = "/book/:isbn";

    public static String LOGIN_TEMPLATE = "/Views/logowanie.vm";
    public static String REGISTER_TEMPLATE = "Views/rejestracja.vm";
    public static String UPLOADBOOK_TEMPLATE = "Views/dodajKsiazke.vm";
    public static String VIEW_BOOKS = "Views/przegladaj2.vm";
    public static String MAIN_TEMPLATE = "Views/index2.vm";
    public static String CHANGEUSERDATA_TEMPLATE = "Views/uzytkownik_dane.vm";
    public static String ONE_BOOK_TEMPLATE = "Views/przegladajKsiazke.vm";


}
