public class TripleDES {
    String[] keys;

    public TripleDES(String key1, String key2, String key3) {
        keys = new String[] {key1, key2, key3};
    }


    public byte[] encrypt(byte[] input) {
        byte[] output;
        output = new DES(keys[0]).encrypt(input);
        output = new DES(keys[1]).decrypt(output);
        output = new DES(keys[2]).encrypt(output);
        return output;
    }

    public byte[] decrypt(byte[] input) {
        byte[] output;
        output = new DES(keys[2]).decrypt(input);
        output = new DES(keys[1]).encrypt(output);
        output = new DES(keys[0]).decrypt(output);
        return output;
    }

}