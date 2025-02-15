package Data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;

public class DB {
    private ArrayList<User> users;
    private ArrayList<Message> messages;

    public DB() {
        messages = new ArrayList<>();
        users = new ArrayList<>();
        users.add(new User("Person1", "password!123"));
    }

    public void createMessage(int id_sender, int id_receiver, String content, String password) {
        long[] text = new long[content.length()];
        User u = users.get(id_receiver);

        for (int i = 0; i < text.length; i++) {
            text[i] = (long) (Math.pow((int) content.charAt(i), u.getPublicKey()[0]) % u.getPublicKey()[1]);
        }

        Message m = new Message(text, createSignature(id_sender, content, password), id_sender); // I need to add the second text here
        messages.add(m);
    }

    private String createSignature(int id_sender, String content, String password) {
        content = hashText(content);
        User u = users.get(id_sender);
        String signature = "";

        for (int i = 0; i < content.length(); i++) {
            signature += String.valueOf(
                Math.pow((int) content.charAt(i), u.getPrivateKey(password)) % u.getPublicKey()[1]);
        }

        return signature;
    }

    public String decryptMessage(int id_reader, int id_message, String password) {
        long exponent = users.get(id_reader).getPrivateKey(password);
        long n = users.get(id_message).getPublicKey()[1];
        long[] deCon = messages.get(id_message).content();
        String content = "";

        for (int i = 0; i < deCon.length; i++) content += String.valueOf(deCon[i]);

        String decryptedText = decryptContent(exponent, n, content);

        return (decryptSignature(id_message, decryptedText)) ? decryptedText : "Message has been compromised";
    }

    private boolean decryptSignature(int message_id, String decryptedContent) {


        return false;
    }

    private String decryptContent(long exponent, long publicN, String content) {
        String decryptedText = "";
        BigInteger biN = BigInteger.valueOf(publicN);

        for (int i = 0; i < content.length(); i++) {
            BigInteger biC = BigDecimal.valueOf(content.charAt(i)).toBigInteger();
            decryptedText += (char) biC.pow((int) exponent).mod(biN).intValue();
        }

        return decryptedText;
    }

    private String hashText(String password) {
        String encoded = "";

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            encoded = Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        return encoded;
    }
}
