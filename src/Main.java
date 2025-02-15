import Data.DB;
import Server.EncrpytMessage;

public class Main {
    private static EncrpytMessage em;
    private static DB db;

    public static void main(String[] args) {
        db = new DB();

        db.createMessage(1, 0,"Du Huan", "password!12345");
        System.out.println(db.decryptMessage(0, 0, "password!123"));
    }
}