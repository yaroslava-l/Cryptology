import org.junit.Assert;
import org.junit.Test;

import java.nio.charset.Charset;

public class JunitTest {
    @Test
    public void testSHA256() {

        String text = "Several cryptocurrencies like Bitcoin use SHA-256 for verifying transactions and calculating proof of work";
        byte[] hash = SHA256.encode(text.getBytes());

        StringBuilder hex = new StringBuilder(hash.length * 2);
        int len = hash.length;
        for (int i = 0 ; i < len ; i++) {
            hex.append(String.format("%02X", hash[i]));
        }
        Assert.assertTrue(hex.toString().equalsIgnoreCase("de86d647b47b2a4f7c23490764b84b54fe8692e41cb11f2e0e03f01f84753272"));
    }
}