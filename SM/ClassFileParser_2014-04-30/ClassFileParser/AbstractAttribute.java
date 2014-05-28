import java.io.*;

/// Abstract attribute represents an attribute from the class
/// file format. Abstract attribute is extended in CodeAttribute &
/// OtherAttribute, which do the actual functionality of the object.
/// This has a static method, checkType, which determines which 
/// object to initialize.
public abstract class AbstractAttribute
{
	protected int attributeNameIndex;
	protected long attributeLength;

	/// Static method to determine the type of the object
	public static AbstractAttribute checkType (DataInputStream dis, 
		ConstantPool cp) throws IOException, InvalidConstantPoolIndex, CodeParsingException
	{
		int temp = dis.readUnsignedShort();
		String s = ((ConstantUtf8)cp.getEntry(temp)).getBytes();
		if (s.equals("Code"))
		{
			return new CodeAttribute(dis, temp, cp); /// Code section!
		}
		else
		{
			return new OtherAttribute(dis, temp);
		}
	}

	/// toString Method()
	public String toString()
	{
		return " AttrNameIdx: " + attributeNameIndex + "  AttrLength: " +  attributeLength;
	}

	/// Getters
	public int getName() { return attributeNameIndex; }
	public long getLength() { return attributeLength; }
}
