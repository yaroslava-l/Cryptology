
import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

public class MillerRabin {
	private static final Random rnd = new Random();

	private static final BigInteger ZERO = BigInteger.ZERO;
	private static final BigInteger ONE = BigInteger.ONE;
	private static final BigInteger TWO = new BigInteger("2");
	private static final BigInteger THREE = new BigInteger("3");

	public static boolean millerRabin(BigInteger n, int maxIterations) {

		if (n.equals(TWO) || n.equals(THREE)) {
			return true;
		}

		// n < 2 or n % 2 = 0 is no prime
		if (n.compareTo(TWO) < 0 || n.mod(TWO).equals(BigInteger.ZERO)) {
			return false;
		}

		// n − 1 = (2^s)*d
		BigInteger d = n.subtract(BigInteger.ONE);

		int s = 0;

		while (d.mod(TWO).equals(BigInteger.ZERO)) {
			d = d.divide(TWO);
			s++;
		}


		for (int i = 0; i < maxIterations; i++) {
			// generate a
			BigInteger a = new BigInteger(n.toByteArray().length, rnd).add(TWO);

			// x ← a^d mod n
			BigInteger x = a.modPow(d, n);

			// x == 1 or x == n − 1
			if (x.equals(BigInteger.ONE) || x.equals(n.subtract(BigInteger.ONE)))
				continue;

			for (int r = 1; r < s; r++)
			{
				// x ← x^2 mod n
				x = x.modPow(TWO, n);

				// if x == 1, return false
				if (x.equals(BigInteger.ONE))
					return false;

				//  if x == n − 1, check next a
				if (x.equals(n.subtract(BigInteger.ONE)))
					break;
			}

			if (!x.equals(n.subtract(BigInteger.ONE)))
				return false;
		}

		return true;
	}


	public static void main(String[] args) {


		Scanner scan = new Scanner(System.in);
		System.out.println("Input number: ");
		BigInteger numb = scan.nextBigInteger();
		//primes = { "1", "3", "3613", "7297", "226673591177742970257407", "2932031007403" };
		//nonPrimes = { "3341", "2932021007403","226673591177742970257405" };
		System.out.printf("Number is prime" + millerRabin(numb, 50) + '\n');
	}
}