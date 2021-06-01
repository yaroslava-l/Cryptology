
import java.math.BigInteger;
import java.util.Scanner;


public class Montgomery {

    private static BigInteger M; // the modulus
    private static BigInteger R; // the auxiliary modulus, power of 2

    private static int reducerBits;// Equal to log2(reducer)= R
    private static BigInteger k;        // Equal to (reducer * reducer^-1 - 1) / n



    // The modulus must be an odd number at least 3
    public Montgomery(BigInteger modulus) {
        // Modulus
        if (modulus.compareTo(BigInteger.ZERO) <= 0)
            throw new IllegalArgumentException("Modulus must be an odd number at least 3");

        this.M = modulus;
        // Reducer
        reducerBits = (modulus.bitLength() / 8 + 1) * 8;  // This is a multiple of 8
        R = BigInteger.ONE.shiftLeft(reducerBits);  // This is a power of 256
        k = R.multiply(R.modInverse(modulus)).subtract(BigInteger.ONE).divide(modulus);
    }

    // Inputs and output are in Montgomery form and in the range [0, modulus)
    public static BigInteger multiply(BigInteger x, BigInteger y) {

        // a = (x * N')&(R - 1) = ((x & R_1) * N') & R_1
        BigInteger temp = x.multiply(y).and(R.subtract(BigInteger.ONE)).multiply(k).and(R.subtract(BigInteger.ONE));
        BigInteger reduced = x.multiply(y).add(temp.multiply(M)).shiftRight(reducerBits);

        BigInteger result = reduced.compareTo(M) > 0 ? reduced.subtract(M) : reduced;
        if (result.signum() < 0 || result.compareTo(M) >= 0) throw new AssertionError();
        return result;
    }


    // Input x (base) and output (power) are in Montgomery form and in the range [0, modulus); input y (exponent) is in standard form
    public static BigInteger pow(BigInteger x, BigInteger y) {
        if (x.signum() < 0 || x.compareTo(M) >= 0) throw new AssertionError();
        if (y.signum() == -1) throw new IllegalArgumentException("Negative exponent");

        int len = y.bitLength();
        BigInteger z = R.mod(M);
        for (int i = 0; i < len; i++) {
            if (y.testBit(i))
                z = multiply(z, x);
            x = multiply(x, x);
        }
        return z;
    }


    public BigInteger convertIn(BigInteger x) {
        return x.shiftLeft(reducerBits).mod(M);
    }

    public BigInteger convertOut(BigInteger x) {
        return x.multiply(R.modInverse(M)).mod(M);
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Modulus: ");
        BigInteger mod = scan.nextBigInteger();

        System.out.println("Input number 1: ");
        BigInteger num1 = scan.nextBigInteger();

        System.out.println("Input number 2: ");
        BigInteger num2 = scan.nextBigInteger();

        System.out.println("Input power: ");
        BigInteger pow = scan.nextBigInteger();

        Montgomery red = new Montgomery(mod);
        BigInteger x = red.convertIn(num1);
        System.out.printf("" + pow(x, pow) + '\n');

        System.out.printf("" + multiply(x, red.convertIn(num2)) + '\n');

    }
}