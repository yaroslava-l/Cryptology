import java.math.BigInteger;
import java.util.Scanner;

import static java.lang.reflect.Array.getLength;

public class KaratsubaMult {
    private static final BigInteger TEN = new BigInteger("10");

    public static BigInteger karatsuba(BigInteger x, BigInteger y) {

        int N = Math.max(x.bitLength(), y.bitLength());

        if (x.compareTo(TEN) < 0 && y.compareTo(TEN) < 0)
            return x.multiply(y);

        if (N % 2 == 1)
            N++;
        N = N / 2;

        // x = a0 + 2^N a1,   y = b0 + 2^N b1
        BigInteger a1 = x.shiftRight(N);
        BigInteger a0 = x.subtract(a1.shiftLeft(N));
        BigInteger b1 = y.shiftRight(N);
        BigInteger b0 = y.subtract(b1.shiftLeft(N));

        // compute sub-expressions
        BigInteger a0b0    = karatsuba(a0, b0);
        BigInteger a1b1    = karatsuba(a1, b1);
        BigInteger ab  = karatsuba(a0.add(a1), b0.add(b1));

        return a0b0.add(ab.subtract(a0b0).subtract(a1b1).shiftLeft(N)).add(a1b1.shiftLeft(2*N));
    }


    public static void main(String[] args) {

        //BigInteger numb1 = new BigInteger("6545448465146517484515489985415158485");
        //BigInteger numb2 = new BigInteger("558488784654848541654156849874984165416");

        Scanner scan = new Scanner(System.in);
        System.out.println("Input number 1: ");
        BigInteger numb1 = scan.nextBigInteger();
        System.out.println("Input number 2: ");
        BigInteger numb2 = scan.nextBigInteger();

        BigInteger c = karatsuba(numb1, numb2);
        System.out.println("Result: " + c);
    }
}


