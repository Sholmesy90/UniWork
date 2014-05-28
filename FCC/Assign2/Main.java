import java.util.*;
import java.io.*;
import java.math.BigInteger;

public class Main
{

	public static void main(String[] args)
	{
		if (args.length != 1)
		{
			System.out.println("Correct usage : java Main" +
									" <file to code/decode>");
		}
		else
		{
			/// Creates an RSA object that instantiates the required
			/// values for the RSA algorithm.
			RSACrypto rsa = new RSACrypto();

			/// Calls the encode function.	
			rsa.encodeFile(args);
			System.out.println("Encoded message in : Encrypted.txt");
			System.out.println("Decoded message in : Decrypted.txt");
		}
	}
}
