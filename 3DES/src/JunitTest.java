import java.math.BigInteger;
import java.util.Base64;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class JunitTest {
    TripleDES tripleDes = new TripleDES("sdfghjkl", "juhgtghj", "ascvfgh");

    @Test
    public void testDes()  {
        String text = "Use the provided TDES/3DES functions for data encryption in various operation modes.";

        byte[] encrypted = tripleDes.encrypt(text.getBytes());
        byte[] decrypted = tripleDes.decrypt(encrypted);
        assertEquals(text, new String(decrypted));
    }

    @Test
    public void testDesEqual()  {
        String text1 = "Use the provided TDES/3DES functions for data encryption in various operation modes.";
        String text2 = "Use the provided TDES/3DES functions for data encryption in various operation modes.";

        byte[] encrypted1 = tripleDes.encrypt(text1.getBytes());
        byte[] encrypted2 = tripleDes.encrypt(text2.getBytes());
        assertEquals(new String(encrypted1), new String(encrypted2));
    }
}
