import java.util.*;
import java.io.*;
import java.math.BigInteger;

public class RSACrypto
{
	private BigInteger modulus;
	private BigInteger totient;
	private BigInteger e;
	private BigInteger d;

	public RSACrypto()
	{
		/// Generates prime numbers randomly within a value of error.
		PrimeGenerator primes = new PrimeGenerator();
		modulus = new BigInteger(Integer.toString(primes.getP1()));
		BigInteger m2 = new BigInteger(Integer.toString(primes.getP2()));
		
		/// Creates a modulus by multiplying the two primes.
		modulus = modulus.multiply(m2);
		System.out.println("Modulus : " + modulus);

		totient = new BigInteger(Integer.toString(primes.getP1()-1));
		BigInteger t2 = new BigInteger(Integer.toString(primes.getP2()-1));
		
		/// Creates a totient by multiplying p1-1 by p2-1
		totient = totient.multiply(t2);
		System.out.println("Totient : " + totient);

		/// Generates a random BigInteger between 1 & the totient
		/// It then generates 'd' by finding the modInv of the two.
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
		e = new BigInteger("17");
		d = modInv(e, totient);
		System.out.println("Mod Inverse of this e & totient: " + d);
	}


	/// This function is used to find the greatest common divisor
	/// using recursion.
	private BigInteger gcd(BigInteger a, BigInteger b)
	{
		if ((a.compareTo(BigInteger.ZERO) == 0) || 
			(b.compareTo(BigInteger.ZERO) == 0))
		{
			return a.add(b);
		}
		else
		{
			return gcd(b, a.mod(b));
		}
	}

	/// Finds the multiplicative mod inverse of two numbers.
	private BigInteger modInv (BigInteger a, BigInteger b)
	{
		BigInteger b0 = b, t, q;
		BigInteger x0 = new BigInteger("0"), x1 = new BigInteger("1");

		if (b.compareTo(BigInteger.ONE) == 0)
		{
			return BigInteger.ONE;
		}
		while (a.compareTo(BigInteger.ONE) == 1)
		{			
			if (b.compareTo(BigInteger.ZERO) == 0)
			{
				b = BigInteger.ONE;
			}
			q = a.divide(b);
			t = b;
			b = a.mod(b);
			a = t;

			t = x0;
			x0 = (x1.subtract(q.multiply(x0)));
			x1 = t;
		}
					

		if (x1.compareTo(BigInteger.ZERO) == -1) /// fact
		{
			x1 = x1.add(b0);
		}
		return x1;
	}

	/// Encodes the file into a set of large numbers
	public void encodeFile (String[] args)
	{
		int fileSize = 0;
		int readCheck = 0;
		BigInteger m;
		try 
		{
			FileInputStream fis = new FileInputStream(args[0]);
			while ((readCheck = fis.read()) != -1)
			{
				fileSize++; /// Finds the length of the file.
			}
			fis.close();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		BigInteger[] eArr = new BigInteger[fileSize];
		BigInteger[] arr = new BigInteger[fileSize];
		try 
		{
			readCheck = 0;
			int k = 0;
			FileInputStream fis = new FileInputStream(args[0]);
			while ((readCheck = fis.read()) != -1)
			{
				arr[k] = new BigInteger(Integer.toString(readCheck));
				k++;
			}
			fis.close();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		for (int l = 0; l < arr.length; l++)
		{
			m = arr[l];
			/// Encryption algorithm! RSA!
			eArr[l] = m.modPow(e, modulus);
		}
		try
		{
			/// Writes it to the file.
			PrintWriter pw = new PrintWriter("Encrypted.txt");
			for (int i = 0; i < eArr.length; i++)
			{
				pw.println(eArr[i]);
			}
			pw.close();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		decodeFile(); /// Calls decode to write the decoded file
	}

	public void decodeFile ()
	{
		int fileSize = 0;
		int readCheck = 0;
		String line;
		try 
		{
			/// Reads the encrypted file for size
			FileReader fr = new FileReader("Encrypted.txt");
			BufferedReader br = new BufferedReader(fr);

			while ((line = br.readLine()) != null)
			{
				fileSize++;
			}
			br.close();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

		BigInteger[] arr = new BigInteger[fileSize];
		try 
		{
			int k = 0;
			FileReader fr = new FileReader("Encrypted.txt");
			BufferedReader br = new BufferedReader(fr);
			while ((line = br.readLine()) != null)
			{
				arr[k] = new BigInteger(line);
				k++;
			}
			br.close();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		BigInteger output;
		String finalString = "";
		
		/// Creates a string that is decoded.
		for (int i = 0; i < arr.length; i++)
		{
			/// Decryption algorithm happens here! RSA!
			output = arr[i].modPow(d, modulus);
			finalString += ((char)output.intValue());
		}

		try
		{
			/// Write to file.
			PrintWriter pw = new PrintWriter("Decrypted.txt");
			pw.println(finalString);
			pw.close();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}