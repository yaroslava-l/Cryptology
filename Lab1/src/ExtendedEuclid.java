import java.math.*;
import java.util.Scanner;

public class ExtendedEuclid {
    private static final BigInteger ZERO = BigInteger.ZERO;
    private static final BigInteger ONE = BigInteger.ONE;

    //d = gcd(x, y), ax + by = d
    public static BigInteger[] ExtendedEuclid(BigInteger a, BigInteger b) {
        BigInteger[] ans = new BigInteger[3];

        if (b.compareTo(ZERO) == 0)  {
            System.out.println(a);
            ans[0] = a;
            ans[1] = ONE;
            ans[2] = ZERO;
        }
        else {
            //recursive function call
            BigInteger d = a.divide(b);
            BigInteger[] ans2 = ExtendedEuclid (b, a.mod(b));
            // bx + (a%b)y = gcd
            // bx + (a%b + b(a/b) - b(a/b))y = gcd
            // bx + (a - b(a/b))y = gcd
            // bx + ay - b(a/b)y = gcd
            // ay + b(x - (a/b)y) = gcd
            ans[0] = ans2[0];
            ans[1] = ans2[2];
            ans[2] = ans2[1].subtract(ans2[2].multiply(d));
        }

        return ans;
    }

    public static void main(String[] args) {
        //BigInteger a = new BigInteger("612");
        //BigInteger b = new BigInteger("342");

        Scanner scan = new Scanner(System.in);
        System.out.println("Input number 1: ");
        BigInteger numb1 = scan.nextBigInteger();
        System.out.println("Input number 2: ");
        BigInteger numb2 = scan.nextBigInteger();

        BigInteger[] res = ExtendedEuclid(numb1, numb2);
        System.out.println("GCD(" + numb1.toString()
                            + ", " + numb2
                            + ") = " + res[0].toString());

        System.out.println(res[0].toString() + " = "
                + res[1].toString() + "*" + numb1
                + " + " + res[2].toString() + "*" + numb2);

    }


}

