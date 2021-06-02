
public class Main {
    public static void main(String[] args) {

        String text = "Several cryptocurrencies like Bitcoin use SHA-256 for verifying transactions and calculating proof of work";
        byte[] hash = SHA256.encode(text.getBytes());

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            hex.append(String.format("%02X", b));
        }
        System.out.println( text);
        System.out.println( hex.toString().toLowerCase());
    }
}