import Data.DB;
import Server.EncrpytMessage;

public class Main {
    private static EncrpytMessage em;
    private static DB db;

    public static void main(String[] args) {
        db = new DB();

        db.createMessage(0, "Du Huan");
        db.createMessage(0, "Du Huan123");
        System.out.println(db.decryptMessage(0, 1, "password!123"));
    }
}