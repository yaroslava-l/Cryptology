import java.util.Base64;

public class Main  {
    public static void main(String[] args) {
        TripleDES tripleDes = new TripleDES("sdfghjkl", "oiuytre", "ascvfgh");

        String text = "Use the provided TDES/3DES functions for data encryption in various operation modes.";
        System.out.println(text);

        byte[] encrypted = tripleDes.encrypt(text.getBytes());
        byte[] decrypted = tripleDes.decrypt(encrypted);

        System.out.println(new String(encrypted));
        //System.out.println(Base64.getEncoder().encodeToString(encrypted));
        System.out.println(new String(decrypted));
    }


}
