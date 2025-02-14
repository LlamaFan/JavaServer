package Data;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class User {
    private String passwordHashed;
    private String name;

    private int[] keys;

    private int pN; // Public key p1
    private int e; // Public key p2, exponent
    private int d; // Private key

    public User(String name, String password) {
        passwordHashed = hashPassword(password);
        this.name = name;

        createKey();
    }

    private String hashPassword(String password) {
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

    private void createKey() {
        int p = 7;
        int q = 19;
        int phiN = (p - 1) * (q - 1);

        for (e = 2; e < phiN; e++)
            if (ggT(e, phiN) == 1) break;

        for (int i = 0; i <= 9; i++) {
            int x = 1 + (i * phiN);

            if (x % e == 0) {
                d = x / e;
                break;
            }
        }

        pN = p * q;
        keys = new int[]{e, pN};
    }

    private int ggT(int a, int b) {
        int i;

        if (a < b) i = a;
        else i = b;

        for (i = i; i > 1; i--)
            if (a % i == 0 && b % i == 0)
                return i;

        return 1;
    }

    public int getPrivateKey(String password) {
        return (passwordHashed.equals(hashPassword(password))) ? d : 0;
    }

    public int[] getPublicKey() {
        return keys;
    }

    public String getName() {
        return name;
    }
}
