import java.io.*;

public class Method
{
	private int accessFlags;
	private int nameIndex;
	private int descriptionIndex;
	private AttributeSet attributes;

	public Method (DataInputStream dis, ConstantPool cp) throws IOException,
													InvalidConstantPoolIndex,
													CodeParsingException
	{
		accessFlags = dis.readUnsignedShort();
		nameIndex = dis.readUnsignedShort();
		descriptionIndex = dis.readUnsignedShort();
		attributes = new AttributeSet(dis, cp);
	}

	public String toString()
	{
		return String.format("%3s%9s%9s   %15s\n", accessFlags, nameIndex, descriptionIndex, attributes);
	}

	public String resolveType(String s)
	{
		String[] data = s.split("\\(");
		String[] data2 = data[1].split("\\)");
		char[] parameters = data2[0].toCharArray();
		String finalString = "";
		for (int i = 0; i < parameters.length; i++)
		{
			char c = parameters[i];
			if (c == 'L')
			{
				c = parameters[i+1];
				while (c != ';')
				{
					c = parameters[i];
					i++;
					if (c != 'L')
						finalString += c;
				}
				finalString = finalString.substring(0,finalString.length()-1);

			}

			switch (c)
			{
				case 'B':
					finalString += "byte";
					break;
				case 'C':
					finalString += "char";
					break;
				case 'D':
					finalString += "double";
					break;
				case 'F':
					finalString += "float";
					break;
				case 'I':
					finalString += "int";
					break;
				case 'J':
					finalString += "long";
					break;
				case 'S':
					finalString += "short";
					break;
				case 'Z':
					finalString += "boolean";
					break;
				case '[':
					finalString += "[]";
						break;
			}
			finalString += " ";
		}	
		if (parameters.length>0)
		{
			finalString = finalString.substring(0, finalString.length()-1);
		}
		else
		{
			finalString = "void";
		}
		return finalString;
	}

	public int getAccessFlags() { return accessFlags; }
	public int getNameIndex() { return nameIndex; }
	public int getDescIndex() { return descriptionIndex; }
	public AttributeSet getAttributeSet() { return attributes; }
}