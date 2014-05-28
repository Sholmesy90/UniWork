import java.util.*;
import java.io.*;
import java.math.BigInteger;

public class Main
{
	public static void main(String[] args)
	{
		PrimeGenerator primes = new PrimeGenerator();
		BigInteger modulus = new BigInteger(Integer.toString(primes.getP1()));
		BigInteger m2 = new BigInteger(Integer.toString(primes.getP2()));
		modulus = modulus.multiply(m2);

		System.out.println("Modulus : " + modulus);

		BigInteger totient = new BigInteger(Integer.toString(primes.getP1()-1));
		BigInteger t2 = new BigInteger(Integer.toString(primes.getP2()-1));
		totient = totient.multiply(t2);

		System.out.println("Totient : " + totient);
		BigInteger e;
		do 
		{
			Random rand = new Random(System.nanoTime());
			do 
			{
				e = new BigInteger(totient.bitLength(), rand);
			}
			while (e.compareTo(totient) >= 0);
		}
		while ((gcd(e,totient)).compareTo(BigInteger.ONE) == 0);
		System.out.println("Random number (e) that is co-prime: " + e);
	}

	public static BigInteger gcd(BigInteger a, BigInteger b)
	{
		if ((a.compareTo(BigInteger.ZERO) == 0) || (b.compareTo(BigInteger.ZERO) == 0))
		{
			return a.add(b);
		}
		else
		{
			return gcd(b, a.mod(b));
		}
	}
}
