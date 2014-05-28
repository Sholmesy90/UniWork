import java.io.*;

public class AttributeSet
{
	private AbstractAttribute[] attributeArray;
	private int attributeCount;

	public AttributeSet(DataInputStream dis, ConstantPool cp) throws IOException, 
								  InvalidConstantPoolIndex, CodeParsingException
	{
		attributeCount = dis.readUnsignedShort();
		attributeArray = new AbstractAttribute[attributeCount];
		for (int i = 0; i < attributeCount; i++)
		{
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

	public int getCount() { return attributeCount; }

	public int getNameAt(int i) { return attributeArray[i].getName(); }

	public AbstractAttribute getAttribute(int i) { return attributeArray[i]; }
}