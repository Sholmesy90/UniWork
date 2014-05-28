import java.io.*;

/// AttributeSet stores a set of attributes that can be either
/// CodeAttribute or OtherAttribute.
public class AttributeSet
{
	private AbstractAttribute[] attributeArray;
	private int attributeCount;

	/// Passes the dis & cp into the set.
	public AttributeSet(DataInputStream dis, ConstantPool cp) throws IOException, 
								  InvalidConstantPoolIndex, CodeParsingException
	{
		attributeCount = dis.readUnsignedShort();
		attributeArray = new AbstractAttribute[attributeCount];
		for (int i = 0; i < attributeCount; i++)
		{
			/// Tests to determine what type of attribute to store in the array.
			attributeArray[i] = AbstractAttribute.checkType(dis, cp);
		}
	}

	public String toString()
	{
		String s = "Num of attributes: " + attributeCount + " ";
		for (int i = 0; i < attributeCount; i++)
		{
			s += attributeArray[i];
		}
		return s;
	}

	/// Getters
	public int getCount() { return attributeCount; }

	public int getNameAt(int i) { return attributeArray[i].getName(); }

	public AbstractAttribute getAttribute(int i) { return attributeArray[i]; }
}