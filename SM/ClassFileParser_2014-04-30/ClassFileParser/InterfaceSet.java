import java.io.*;

public class InterfaceSet
{
	private int[] interfaceArray;
	private int interfaceCount;

	public InterfaceSet(DataInputStream dis) throws IOException
	{
		interfaceCount = dis.readUnsignedShort();
		interfaceArray = new int[interfaceCount];
		for (int i = 0; i < interfaceCount; i++)
		{
			interfaceArray[i] = dis.readUnsignedShort();
		}
	}

	public String toString()
	{
		String s =  "Num of Interfaces: " + interfaceCount + "\n" +
					"Interface #\n" +
				    "-----------------------\n";
		for (int i = 0; i < interfaceCount; i++)
		{
			s += interfaceArray[i];
		}
		return s;
	}
}