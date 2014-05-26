import java.io.*;

public class OtherAttribute extends AbstractAttribute
{
	private byte[] info;
	
	public OtherAttribute(DataInputStream dis, int i) throws IOException
	{
		attributeNameIndex = i;
		attributeLength = (long)dis.readUnsignedShort() << 16 | dis.readUnsignedShort();
		info = new byte[(int)attributeLength];
		dis.readFully(info);
	}
}