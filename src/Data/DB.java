package Data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

public class DB {
    private ArrayList<User> users;
    private ArrayList<Message> messages;

    public DB() {
        messages = new ArrayList<>();
        users = new ArrayList<>();
        users.add(new User("Person1", "password!123"));
    }

    public void createMessage(int id_receiver, String content) {
        long[] text = new long[content.length()];
        User u = users.get(id_receiver);

        for (int i = 0; i < text.length; i++) {
            text[i] = (long) (Math.pow((int) content.charAt(i), u.getPublicKey()[0]) % u.getPublicKey()[1]);
        }

        Message m = new Message(text, null); // I need to add the second text here
        messages.add(m);
    }

    public String decryptMessage(int id_reader, int id_message, String password) {
        String decryptedText = "";
        User u = users.get(id_reader);
        Message m = messages.get(id_message);
        BigInteger biN = BigInteger.valueOf(u.getPublicKey()[1]);

        for (int i = 0; i < m.content().length; i++) {
            BigInteger biC = BigDecimal.valueOf(m.content()[i]).toBigInteger();
            decryptedText += (char) biC.pow(u.getPrivateKey(password)).mod(biN).intValue();
        }

        return decryptedText;
    }
}
