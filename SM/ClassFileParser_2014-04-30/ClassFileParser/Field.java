import java.io.*;

/// Stores all the neccesary field information.
public class Field
{
	private int accessFlags;
	private int nameIndex;
	private int descriptionIndex;
	private AttributeSet attributes;

	public Field (DataInputStream dis, ConstantPool cp) throws IOException,
													 InvalidConstantPoolIndex,
													 CodeParsingException
	{
		accessFlags = dis.readUnsignedShort();
		nameIndex = dis.readUnsignedShort();
		descriptionIndex = dis.readUnsignedShort();
		attributes = new AttributeSet(dis, cp);
	}
}