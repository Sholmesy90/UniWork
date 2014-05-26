import java.io.*;

public abstract class AbstractAttribute
{
	protected int attributeNameIndex;
	protected long attributeLength;

	public static AbstractAttribute checkType (DataInputStream dis, 
		ConstantPool cp) throws IOException, InvalidConstantPoolIndex, CodeParsingException
	{
		int temp = dis.readUnsignedShort();
		String s = ((ConstantUtf8)cp.getEntry(temp)).getBytes();
		if (s.equals("Code"))
		{
			return new CodeAttribute(dis, temp, cp);
		}
		else
		{
			return new OtherAttribute(dis, temp);
		}
	}

	public String toString()
	{
		return " AttrNameIdx: " + attributeNameIndex + "  AttrLength: " +  attributeLength;
	}

	public int getName() { return attributeNameIndex; }
	public long getLength() { return attributeLength; }
}
