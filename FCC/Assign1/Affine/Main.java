import java.io.*; 
import java.util.*; 
import java.math.*;

public class Main
{
	public static void main(String[] args)
	{
		try
		{
			//Read from the file
			FileInputStream fstream = new FileInputStream(args[0]); 
			DataInputStream in = new DataInputStream(fstream); 
			BufferedReader br = new BufferedReader(new InputStreamReader(in)); 

			String strLine = "";
			String temp;
			while ((temp = br.readLine()) != null)
			{
				strLine += temp;
			}
			//Convert to uppercase for uniformality 
			strLine = strLine.toUpperCase();

			int choice = -1;
			int keyA = 0;
			int keyB = 0;

			//Get user choice of function
			Scanner scanner = new Scanner(System.in);
			while (choice != 1 && choice != 2 && choice != 3 && choice != 0)
			{
				System.out.println("\n--Affine Encryption Functions--");
				System.out.println("(1) Encrypt \n(2) Decrypt \n(3) Calculate Letter Frequency \n(0) Quit\n");
				choice = Integer.parseInt(scanner.nextLine());
			}

			//Gets the key from the user
			if (choice != 0 && choice != 3)
			{
				System.out.println("Enter values for the key\n");

				while (keyA != 1 && keyA != 3 && keyA != 5 && keyA != 7 
					&& keyA != 9 && keyA != 11 && keyA != 15 && keyA != 17 
					&& keyA != 19 && keyA != 21 && keyA != 23 && keyA != 25)
				{
					System.out.println("Enter the value of a (Must be 1,3,5,7,9,11,15,17,19,21,23 or 25) : ");
					keyA = Integer.parseInt(scanner.nextLine());
				}

				System.out.println("Enter the value of b (Any value) : ");
				keyB = Integer.parseInt(scanner.nextLine());

				scanner.close();

				System.out.println("\n-----------------------\n");

				if (choice == 1)
				{
					System.out.println("Your key is (a = " + keyA + " : b = " + keyB + ")");
					System.out.println("You will need this for decryption!\n");
					affineEncrypt(keyA, keyB, strLine);
				}
				else if (choice == 2)
				{
					affineDecrypt(keyA, keyB, strLine);
				}
			}
			else if (choice == 3)
			{
				letterFrequency(strLine);
			}
		}
		catch (IOException e)
		{
			System.out.println("Error reading file " + e.getMessage());
		}
	}

	public static void affineEncrypt(int a, int b, String str)
	{
		//Converts the char to the corresponding output as per key
		char[] array = str.toCharArray();
		for (int i = 0; i < array.length; i++)
		{
			char c = array[i];
			int temp = (int)c - 'A';
			int temp2 = (temp*a) + b;
			int temp3 = temp2 % 26;
			c = (char)(temp3 + 'A');
			array[i] = c;
		}

		//Outputs to file
		String finalString = new String(array);
		try
		{
			PrintWriter pw = new PrintWriter ("EncryptedMessage.txt");
			pw.println(finalString);
			pw.close();  
			System.out.println("Encrypted string is : \n\n" + finalString);
			System.out.println("\n-----------------------\n");
			System.out.println("Written to EncryptedMessage.txt");
			System.out.println("Use this file for Decryption");
		}
		catch (IOException e)
		{
			System.out.println("Error writing file " + e.getMessage());
		}
	}

	public static void affineDecrypt(int a, int b, String str)
	{
		char[] array = str.toCharArray();

		//BigInteger has a nice function for finding the modInverse.
		BigInteger big1, big2, big3;
		big1 = new BigInteger("" + a);
		big2 = new BigInteger("26");
		big3 = big1.modInverse(big2);

		//Get int value of this modInverse
		int aInv = big3.intValue();	

		//Decrypt as per the key
		for (int i = 0; i < array.length; i++)
		{
			char c = array[i];
			int temp = c - 'A';
			int temp2 = temp - b;
			int temp3 = aInv * temp2;
			int temp4 = temp3 % 26;
			if (temp4 < 0)
			{
				temp4 += 26;
			}
			c = (char)(temp4 + 'A');
			array[i] = c;
		}
		String finalString = new String(array);
		
		//Write to file
		try
		{
			PrintWriter pw = new PrintWriter ("DecryptedMessage.txt");
			pw.println(finalString);
			pw.close();  
			System.out.println("Decrypted string is : \n\n" + finalString);
			System.out.println("\n-----------------------\n");
			System.out.println("Written to DecryptedMessage.txt");
			System.out.println("Use this file for Re-Encryption");
		}
		catch (IOException e)
		{
			System.out.println("Error writing file " + e.getMessage());
		}
	}

	//Calculates frequency of letters in input file
	public static void letterFrequency(String str)
	{
		int[] alphabet = new int[26];
		char[] array = str.toCharArray();
		String finalString = "";

		//Uses an array as a look up table.
		//array[0] represents # of a's, array[1] # of b's etc
		for (int i = 0; i < array.length; i++)
		{
			char c = array[i];
			int temp = c - 'A';
			alphabet[temp] = alphabet[temp] + 1;
		}

		for (int i = 0; i < alphabet.length; i++)
		{
			char c = (char)((int)'A' + i);
			finalString += " " + c + " : " + alphabet[i];
		}
		
		//Write to file.
		try
		{
			PrintWriter pw = new PrintWriter ("LetterFreq.txt");
			pw.println(finalString);
			pw.close();  
			System.out.println("\nLetter Frequency : \n\n" + finalString);
			System.out.println("\n-----------------------\n");
			System.out.println("Written to LetterFreq.txt");
		}
		catch (IOException e)
		{
			System.out.println("Error writing file " + e.getMessage());
		}
	}
}