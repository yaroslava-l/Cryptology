
import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

public class FermaTest {


    private static final BigInteger TWO = new BigInteger("2");
    private static final BigInteger THREE = new BigInteger("3");

    private static BigInteger getRandomFermaBase(BigInteger n) {
        while (true) {
            // generate a
            final BigInteger a = new BigInteger (n.bitLength(), new Random());
            // must have 1 <= a < n
            if (BigInteger.ONE.compareTo(a) <= 0 && a.compareTo(n) < 0) {
                return a;
            }
        }
    }

    public static boolean checkPrime(BigInteger n, int maxIterations)
    {
        if (n.equals(TWO) || n.equals(THREE)) {
            return true;
        }

        if (n.compareTo(TWO) < 0 || n.mod(TWO).equals(BigInteger.ZERO)) {
            return false;
        }
        if (n.equals(BigInteger.ONE))
            return false;
        //a^(n − 1) ≡ 1 ( mod n )
        for (int i = 0; i < maxIterations; i++) {
            BigInteger a = getRandomFermaBase(n);
            a = a.modPow(n.subtract(BigInteger.ONE), n);

            if (!a.equals(BigInteger.ONE))
                return false;
        }

        return true;
    }
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Input number: ");
        BigInteger numb = scan.nextBigInteger();

        System.out.printf("Number is prime" + checkPrime(numb, 50) + '\n');
    }
}