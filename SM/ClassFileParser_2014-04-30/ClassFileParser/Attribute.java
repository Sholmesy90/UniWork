import java.io.*;

public class Attribute
{
	private int attributeNameIndex;
	private int attributeLength;
	private byte[] info;

	public Attribute (DataInputStream dis)
	{
		attributeNameIndex = dis.readInputStream();
		attributeLength = dis.readUnsignedShort();
		for (int i = 0; i < attributeLength; i++)
		{
			info[i] = dis.readUnsignedShort();
		}
	}
}