import java.util.*;
import java.io.*;
import java.math.BigInteger;

public class PrimeGenerator
{
	int p1;
	int p2;
	public PrimeGenerator()
	{
		/// Randomly select 2 primes between 10000 & 100000.
		System.out.println("Generating Primes...");
		do 
		{
			p1 = generatePrime();
		}
		while (!confirmedPrime(p1));
		System.out.println("Prime #1 : " + p1);
		do 
		{
			p2 = generatePrime();
		}
		while (!confirmedPrime(p2));
		System.out.println("Prime #2 : " + p2);
		//p1 = 61;
		//p2 = 53;
	}

	/// Confirms within a value of 1/2^10 chance that the number is prime.
	private boolean confirmedPrime(int random)
	{
		boolean isTrue = true;
		for (int i = 0; i < 10; i ++)
		{
			int a = 0;
			while (a % 2 == 0)
			{
				a = getRandom(1, random);
			}
			if(!checkPrime(random, a))
			{
				isTrue = false;
			}
		}
		return isTrue;
	}

	/// Generates a prime number that is at least 50% sure it is prime.
	private int generatePrime()
	{
		int random = 0;
		int a = 0;
		do
		{
			a = 0;
			while (random % 2 == 0)
			{
				random = getRandom(10000, 100000);
			}
			while (a % 2 == 0)
			{
				a = getRandom(1, random);
			}
		} 
		while (!checkPrime(random, a));

		return random;
	}

	/// Checks if probably prime.
	private boolean checkPrime(int p, int a)
	{
		int exponent = (p - 1)/2;
		BigInteger m = new BigInteger(Integer.toString(p));
		BigInteger e = new BigInteger(Integer.toString(exponent));
		BigInteger r = new BigInteger(Integer.toString(a));
		r = r.modPow(e, m);
		if ((r.intValue() == 1)||(r.intValue() == p-1))
			return true;
		else
			return false;
	}

	private int getRandom(int min, int max)
	{
		Random rand = new Random(System.nanoTime());
		return  rand.nextInt(max - min) + min;
	}

	/// Getters.
	public int getP1() { return p1; }
	public int getP2() { return p2; }
}