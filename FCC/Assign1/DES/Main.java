import java.io.*; 
import java.util.*; 
import java.math.*;

public class Main
{

	
//The following declarations are look up tables used in the DES algorithm:

private static int[] pc1 = {57, 49, 41, 33, 25, 17, 9,
		                  1, 58, 50, 42, 34, 26, 18,
		                  10, 2, 59, 51, 43, 35, 27,
		                  19, 11, 3, 60, 52, 44, 36,
		              	  63, 55, 47, 39, 31, 23, 15,
		               	   7, 62, 54, 46, 38, 30, 22,
		                  14, 6, 61, 53, 45, 37, 29,
		              	  21, 13, 5, 28, 20, 12, 4};

private static int[] pc2 = {14, 17, 11, 24, 1, 5,
		                     3, 28, 15, 6, 21, 10,
		                    23, 19, 12, 4, 26, 8,
		                    16, 7, 27, 20, 13, 2,
		                    41, 52, 31, 37, 47, 55,
		                    30, 40, 51, 45, 33, 48,
		                    44, 49, 39, 56, 34, 53,
		                    46, 42, 50, 36, 29, 32};

private static int[] ip = { 58, 50, 42, 34, 26, 18, 10, 2,
				            60, 52, 44, 36, 28, 20, 12, 4,
				            62, 54, 46, 38, 30, 22, 14, 6,
				            64, 56, 48, 40, 32, 24, 16, 8,
				            57, 49, 41, 33, 25, 17,  9, 1,
				            59, 51, 43, 35, 27, 19, 11, 3,
				            61, 53, 45, 37, 29, 21, 13, 5,
				            63, 55, 47, 39, 31, 23, 15, 7};

private static int[] ipInv = {40, 8, 48, 16, 56, 24, 64, 32,
					        39, 7, 47, 15, 55, 23, 63, 31,
					        38, 6, 46, 14, 54, 22, 62, 30,
					        37, 5, 45, 13, 53, 21, 61, 29,
					        36, 4, 44, 12, 52, 20, 60, 28,
					        35, 3, 43, 11, 51, 19, 59, 27,
					        34, 2, 42, 10, 50, 18, 58, 26,
					        33, 1, 41,  9, 49, 17, 57, 25};


private static int[] ebit = {32,  1,  2,  3,  4,  5,
			                  4,  5,  6,  7,  8,  9,
			                  8,  9, 10, 11, 12, 13,
			                 12, 13, 14, 15, 16, 17,
			                 16, 17, 18, 19, 20, 21,
			                 20, 21, 22, 23, 24, 25,
			                 24, 25, 26, 27, 28, 29,
			                 28, 29, 30, 31, 32, 1};

private static int[][] s1 =  { {14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7},
							   {0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8},
							   {4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0},
							   {15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13}};

private static int[][] s2 =  { {15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10},
							   {3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5},
							   {0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15},
							   {13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9}};

private static int[][] s3 =  { {10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8},
							   {13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1},
							   {13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7},
							   {1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12}};

private static int[][] s4 =  { {7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15},
							   {13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9},
							   {10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4},
							   {3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14}};

private static int[][] s5 =  { {2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9},
							   {14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6},
							   {4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14},
							   {11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3}};

private static int[][] s6 =  { {12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11},
							   {10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8},
							   {9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6},
							   {4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13}};

private static int[][] s7 =  { {4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1},
							   {13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6},
							   {1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2},
							   {6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12}};

private static int[][] s8 =  { {13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7},
							   {1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2},
							   {7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8},
							   {2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11}};

private static int[] p1 = {16, 7, 20, 21,
                         29, 12, 28,  17,
                          1, 15, 23,  26,
                          5, 18, 31,  10,
                          2,  8, 24,  14,
                         32, 27,  3,   9,
                         19, 13, 30,   6,
                         22, 11,  4,  25};

	public static void main(String[] args)
	{
		String strLine = "";
		String inKey = "";
		int choice = 0;

		//Read the password and choice from the user.
		try 
		{
			int keyLength = 0;
			Scanner scanner = new Scanner(System.in);
			while (keyLength < 8)
			{
				System.out.println("Enter passkey of at least 8 characters");
				inKey = scanner.nextLine();
				keyLength = inKey.length();
			}
			inKey = inKey.substring(0,8);
		
			while (choice != 1 && choice != 2)
			{
				System.out.println("(1)Encrpyt Message\n(2)Decrypt Message");
				choice = Integer.parseInt(scanner.nextLine());
			}
			scanner.close();
			
			//Read the file provided from the command line
			FileInputStream fstream = new FileInputStream(args[0]); 
			DataInputStream in = new DataInputStream(fstream); 
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String temp;

			//Iterate over every line in the file
			while ((temp = br.readLine()) != null)
			{
				strLine += temp;
			}	
		}

		catch (IOException e)
		{
			System.out.println("Error reading file or user input : " 
								+ e.getMessage());
		}

		//Convert the password provided by the user into a set of subkeys
		int[][] kNumArray = getKeys(inKey);
		int[][] messages;
		String finalString = "";

		//Determine whether to encode or decode based on user choice
		if (choice == 1)
		{
			messages = getMessages(strLine, kNumArray, false);

			//Write binary conversion obtained to file
			for (int i = 0; i < messages.length; i++)
			{
				//Convert the 2D array into a string to make it easier to write
				finalString += convertToString(messages[i]);
			}
			try
			{
				System.out.println("Written to EncrpytedMessage.txt");
				PrintWriter pw = new PrintWriter ("EncryptedMessage.txt");
				pw.println(finalString);
				pw.close();  
			}
			catch (IOException e)
			{
				System.out.println("Error writing to file : " 
									+ e.getMessage());
			}
		}
		else
		{
			messages = getMessages(strLine, kNumArray, true);
			for (int i = 0; i < messages.length; i++)
			{
				//Convert the 2D array into a string to make it easier to write
				finalString += convertToString(messages[i]);
			}
			//Remove the trailing 1 followed by 0000000000 etc from the string
			finalString = removeTrailing(finalString);

			//Convert the binary to characters
			finalString = convertToText(finalString);
			
			//Write to file
			try
			{
				System.out.println("Written to DecrpytedMessage.txt");
				PrintWriter pw = new PrintWriter ("DecryptedMessage.txt");
				pw.println(finalString);
				pw.close();  
			}
			catch (IOException e)
			{
				System.out.println("Error writing to file : " + e.getMessage());
			}
		}
	}


	//Converts a binary string to a character string
	private static String convertToText(String str)
	{
		String output = "";
		for(int i = 0; i <= str.length() - 8; i+=8)
		{
		    int k = Integer.parseInt(str.substring(i, i+8), 2);
		    output += (char) k;
		}   
		return output;
	}

	//Removes the trailing 1 followed by 1 or more 0's from the binary
	private static String removeTrailing(String str)
	{
		while (str.charAt((str.length()-1)) == '0')
		{
			str = str.substring(0, str.length()-1);
		}
		str = str.substring(0, str.length()-1);
		return str;
	}

	//Converts the array of ints that made up our binary into binary string
	private static String convertToString(int[] message)
	{	
		String temp = "";
		for (int j = 0; j < message.length; j++)
		{
			temp += message[j];
		}
		return temp;
	}

	//Method used by Decrypt & Encrypt to encode the message
	//Takes the input from the user (boolean), the set of keys and the file in 1 string
	private static int[][] getMessages(String strLine, int[][] kNumArray, boolean reverse)
	{
		String binary;
		if (reverse == false)
		{
			byte[] bytes = strLine.getBytes();
			binary = getBits(bytes);
		}
		else
		{
			binary = strLine;
		}

		//Buffer the amount to fill to 64 bits if encoding
		//Use a 1, and then a sequence of 0s to fill the 64 bit slots.
		if (reverse == false)
		{
			binary += "10";
			int remainder = binary.length() % 64;

			for (int i = 0; i < (64-remainder); i++)
			{
				binary += "0";
			}
		}	

		//Get number of 64 bit messages
		int numOfMessages = (binary.length()/64);

		//Create a 2D array to store the messages
		int[][] blocks = new int[numOfMessages][64];
		int counter = 0;

		for (int i = 0; i < numOfMessages; i++)
		{
			//Deal with 64 bit messages at a time
			String subStr = binary.substring(counter, counter+64);
			for (int j = 0; j < 64; j++)
			{
				//Convert string char value to integer value for the int array
				blocks[i][j] = Character.getNumericValue(subStr.charAt(j));
			}	
			counter += 64;

			//Permutate the message across the ip table
			int[] ipTrans = permutate(blocks[i], 64, 2);
			
			//Split the result in two
			int[][] leftIpTrans = new int[17][32];
			int[][] rightIpTrans = new int[17][32];
			for (int j = 0; j < 64; j++)
			{
				if (j < 32)
				{
					leftIpTrans[0][j] = ipTrans[j];
				}
				else
				{
					rightIpTrans[0][j-32] = ipTrans[j];
				}
			}

			//Operate on the result with the subkeys providewd
			for (int j = 1; j <= 16; j++)
			{
				//As per DES algorithm : 
				leftIpTrans[j] = rightIpTrans[j-1];

				//Permutate against eBit table
				int[] eBitArray = permutate(rightIpTrans[j-1], 48, 3);
				
				//XOR the key against the result
				//This is done IN REVERSE if decoding
				int[] xorArray = new int[48];
				for (int k = 0; k < 48; k++)
				{
					if (reverse)
					{
						xorArray[k] = kNumArray[17-j][k] ^ eBitArray[k];
					}
					else
					{
						xorArray[k] = kNumArray[j][k] ^ eBitArray[k];
					}
				}

				//Perform the SBOX lookup algorithms.
				int shift = 0;
				String sBoxString = "";
				for (int k = 1; k <= 8; k++)
				{
					sBoxString += getSBox(xorArray, shift, k);
					shift = shift + 6;
				}		
				int[] sBoxArray = new int[32];
				for (int k = 0; k < 32; k++)
				{
					sBoxArray[k] = Character.getNumericValue(sBoxString.charAt(k));
				}

				//Permutate the result against the P table
				int[] finalArray = permutate(sBoxArray, 32, 4);

				//XOR against the result
				for (int k = 0; k < 32; k++)
				{
					rightIpTrans[j][k] = leftIpTrans[j-1][k] ^ finalArray[k];
				}
			}

			//Combine the split into a 64 bit sum
			int[] lastPermArray = new int[64];
			for (int j = 0; j < 32; j++)
			{
				lastPermArray[j] = rightIpTrans[16][j];
			}
			for (int j = 0; j < 32; j++)
			{
				lastPermArray[j+32] = leftIpTrans[16][j];
			}

			//Permutate one final time against ipInverse and return the result
			blocks[i] = permutate(lastPermArray, 64, 5);
		}
		return blocks;
	}

	private static String getSBox(int[] xorArray, int shift, int k)
	{
		String sBoxArray = "";
		String firstLast = "";
		String middle = "";

		//Grab the first and last digit. Convert to decimal.
		//use the decimal to get the row for the SBOX look up
		firstLast += xorArray[0+shift];
		firstLast += xorArray[5+shift];
		int row = Integer.parseInt(firstLast, 2);

		//Grab the middle digits, convert to decimal.
		//use the decimal to get the column for the SBOX look up
		middle += xorArray[1+shift];
		middle += xorArray[2+shift];
		middle += xorArray[3+shift];
		middle += xorArray[4+shift];
		int col = Integer.parseInt(middle, 2);
		
		//Lookup
		String sBox = "";
		switch (k)
		{
			case 1:
				sBox += s1[row][col];
				break;
			case 2:
				sBox += s2[row][col];
				break;
			case 3:
				sBox += s3[row][col];
				break;
			case 4:
				sBox += s4[row][col];
				break;
			case 5:
				sBox += s5[row][col];
				break;
			case 6:
				sBox += s6[row][col];
				break;
			case 7:
				sBox += s7[row][col];
				break;
			case 8:
				sBox += s8[row][col];
				break;
		}
		//Convert to binary string
		int temp = Integer.parseInt(sBox);
		sBoxArray = Integer.toBinaryString(temp);

		//Add leading 0's removed from integer conversion
		while (sBoxArray.length() < 4)
		{
			sBoxArray = "0" + sBoxArray;
		}
		return sBoxArray;
	}

	private static int[][] getKeys(String inKey)
	{	
		//Get the binary sequence of the password
		byte[] keyBytes = inKey.getBytes();
		String key = getBits(keyBytes);
	
		int[] binaryKey = new int[64];

		//Convert binary string to array of individual bits
		for (int i = 0; i < binaryKey.length; i++)
		{
			binaryKey[i] = Character.getNumericValue(key.charAt(i));
		}

		//Permutate the input against the pc1 table
		int[] binaryKey56 = permutate(binaryKey, 56, 1);

		int[][] cNumArray = new int[17][28];
		int[][] dNumArray = new int[17][28];

		//Split into two keys
		for (int i = 0; i < 56; i++)
		{
			if (i < 28)
			{
				cNumArray[0][i] = binaryKey56[i];
				//cNumArray[0][i] = 1;
			}
			else
			{
				dNumArray[0][i-28] = binaryKey56[i];
				//dNumArray[0][i-28] = 1;
			}
		}

		//Determine whether to left shift one or two
		//Based on table provided with DES
		for (int i = 1; i <= 16; i++)
		{
			if (numShift(i) == 2)
			{
				cNumArray[i] = blockCreator(cNumArray[i-1], 2);
				dNumArray[i] = blockCreator(dNumArray[i-1], 2);
			}
			else if (numShift(i) == 1)
			{
				cNumArray[i] = blockCreator(cNumArray[i-1], 1);
				dNumArray[i] = blockCreator(dNumArray[i-1], 1);
			}
		}

		//Recombine & permutate left and right key against pc2
		int[][] kNumArray = new int[17][48];
		for (int i = 1; i <= 16; i++)
		{
			kNumArray[i] = permutate2(cNumArray[i], dNumArray[i]);
		}
		return kNumArray;
	}

	public static String getBits(byte[] bytes)
	{
		//Converts array of bytes into a binary string
		StringBuilder binary = new StringBuilder();
		for (byte b : bytes)
		{
			int val = b;
			for (int i = 0; i < 8; i++)
			{
				binary.append((val & 128) == 0 ? 0 : 1);
				val <<= 1;
			}
		}
		String returnString = "" + binary;
		return returnString;
	}

	public static int[] permutate(int[] key, int num, int table)
	{
		//Generic permutation function to handle looking up various tables.
		int[] returnKey = new int[num];
		int lookup = 0;
		for (int i = 0; i < num; i++)
		{	
			switch (table)
			{
				case 1:
					lookup = pc1[i];
					break;
				case 2:
					lookup = ip[i];
					break;
				case 3:
					lookup = ebit[i];
					break;
				case 4:
					lookup = p1[i];
					break;
				case 5:
					lookup = ipInv[i];
					break;
			}
			returnKey[i] = key[lookup-1];
		}
		return returnKey;
	}

	public static int[] permutate2(int[] keyC, int[] keyD)
	{
		//Same as above, handles two arrays. used for PC-2
		int[] returnKey = new int[48];
		int lookup = 0;
		for (int i = 0; i < 48; i++)
		{	
			lookup = pc2[i];
			if (lookup < 29)
			{
				returnKey[i] = keyC[lookup-1];
			}
			else
			{
				returnKey[i] = keyD[lookup-29];
			}
		}
		return returnKey;
	}


	public static int[] blockCreator(int[] prevArray, int num)
	{
		//Shift the values of the key left x amount
		int[] ret = new int[28];
		
		for (int i = 0; i < ret.length-num; i++)
		{
			ret[i] = prevArray[i+num];
		}
		if (num == 1)
		{
			ret[27] = prevArray[0];
		}
		else
		{
			ret[27] = prevArray[1];
			ret[26] = prevArray[0];
		}
		return ret;
	}

	public static int numShift(int i)
	{
		//Lookup table to determine how much to left shift
		int num = 0;
		switch (i)
		{
			case 1: case 2: case 9: case 16:
				num = 1;
				break;
			case 3: case 4: case 5: case 6: case 7:case 8:
			case 10: case 11: case 12: case 13: case 14:case 15:
				num = 2;
				break;
		}
		return num;
	}
}
