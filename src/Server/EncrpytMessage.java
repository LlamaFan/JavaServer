package Server;

import java.math.BigDecimal;
import java.math.BigInteger;

public class EncrpytMessage {
    private final String clearText = "Hello, this is a message!";
    private long[] encryptedText;
    private String decryptedText;

    private int p = 7;
    private int q = 19;

    private int pN;
    private int phiN;
    private int e;
    private int d;

    private int len;

    public EncrpytMessage() {
        pN = p * q;
        phiN = (p - 1) * (q - 1);

        //e = findPrimeNumber(phiN); I later on need to change to a more random prime number TODO
        // d = (1 + n * phiN) / e;
        for (e = 2; e < phiN; e++)
            if (ggT(e, phiN) == 1) break;

        for (int i = 0; i <= 9; i++) {
            int x = 1 + (i * phiN);

            if (x % e == 0) {
                d = x / e;
                break;
            }
        }

        encryptMessage();
        decryptMessage();

        System.out.println("Decrypted Text: " + decryptedText);
    }

    private void encryptMessage() {
        int bS = 4;
        //len = (int) Math.ceil((double) clearText.length() / bS);
        encryptedText = new long[clearText.length()];

        for (int i = 0; i < encryptedText.length; i++) {
            encryptedText[i] = (long) (Math.pow((int) clearText.charAt(i), e) % pN);
        }
    }

    private void decryptMessage() {
        decryptedText = "";

        BigInteger biN = BigInteger.valueOf(pN);

        for (int i = 0; i < encryptedText.length; i++) {
            BigInteger biC = BigDecimal.valueOf(encryptedText[i]).toBigInteger();
            decryptedText += (char) biC.pow(d).mod(biN).intValue();
        }
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
}
