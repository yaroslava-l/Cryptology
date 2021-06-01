import java.math.BigInteger;
import java.util.Scanner;

public class BinaryPower {

    private static final BigInteger ZERO = BigInteger.ZERO;
    private static final BigInteger ONE = BigInteger.ONE;
    private static final BigInteger TWO = new BigInteger("2");

    public static BigInteger binpow(BigInteger n, BigInteger pow, BigInteger mod) {
        n = n.mod(mod);

        //n^1, n^2, n^4, n^8, n^16,...
        if (pow.equals(ZERO)){
            return ONE;
        } else if (pow.mod(TWO).equals(ZERO)) {
            return binpow((n.multiply(n)).mod(mod), pow.divide(TWO), mod);
        }
        else return (n.multiply(binpow(n, pow.subtract(ONE), mod))).mod(mod);
    }

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        System.out.println("Input number: ");
        BigInteger numb = scan.nextBigInteger();

        System.out.println("Input degree: ");
        BigInteger deg = scan.nextBigInteger();

        System.out.println("Input mod: ");
        BigInteger mod = scan.nextBigInteger();

        System.out.println(binpow(numb, deg, mod));
    }
}
