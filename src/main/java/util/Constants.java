package util;

import freemarker.cache.StrongCacheStorage;

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
    public static String SETTINGS = "/settings";
    public static String EDIT = "/changeBook/:isbn";
    public static String EDIT_BOOK = "/editbook/:isbn";
    public static String DELETE_BOOK = "/deletebook/:isbn";
    public static String RESERVED_BOOKS = "/reserved";
    public static String BORROWED_BOOKS = "/borrowed";
    public static String DELETE_RESERVED_BOOK = "/reserved/:id";
    public static String PROLONG_BORROWED_BOOK = "/borrowed/:id";
    public static String PENALTIES = "/penalties";
    public static String BORROWING = "/confirmBorrowing";
    public static String CONFIRM_BORROWING = "/confirmBorrowing/:id";
    public static String RETURNING = "/confirmReturn";
    public static String CONFIRM_RETURNING = "/confirmReturn/:id";
    public static String PAYMENT = "/confirmPayment";
    public static String CONFIRM_PAYMENT = "/confirmPayment/:id";

    public static String LOGIN_TEMPLATE = "/Views/logowanie.vm";
    public static String REGISTER_TEMPLATE = "Views/rejestracja.vm";
    public static String UPLOADBOOK_TEMPLATE = "Views/dodajKsiazke.vm";
    public static String VIEW_BOOKS = "Views/przegladaj2.vm";
    public static String MAIN_TEMPLATE = "Views/index2.vm";
    public static String CHANGEUSERDATA_TEMPLATE = "Views/uzytkownik_dane.vm";
    public static String ONE_BOOK_TEMPLATE = "Views/przegladajKsiazke.vm";
    public static String EDIT_BOOK_TEMPLATE = "Views/edytujKsiazke.vm";
    public static String RESERVED_BOOKS_TEMPLATE = "Views/uzytkownik_rezerwacje.vm";
    public static String BORROWED_BOOKS_TEMPLATE = "Views/uzytkownik_wypozyczenia.vm";
    public static String PENALTIES_TEMPLATE = "Views/uzytkownik_oplaty.vm";
    public static String RESERVATION_LIST_TEMPLATE = "Views/pracownik_potwierdz_wyp.vm";
    public static String RETURNING_LIST_TEMPLATE = "Views/pracownik_potwierdz_zwr.vm";
    public static String PAYMENTS_LIST_TEMPLATE = "Views/pracownik_potwierdz_opl.vm";


}
