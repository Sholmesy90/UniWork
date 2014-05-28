import java.io.*;

/// This class is used to represent a method object which can be used to 
/// determien if the method calls anything, through the AttributeSet 
/// which will have a section of CodeAttribute if it calls another method
public class Method
{
	private String className;
	private int accessFlags;
	private int nameIndex;
	private int descriptionIndex;
	private AttributeSet attributes;

	public Method (DataInputStream dis, ConstantPool cp, String inClass) throws IOException,
													InvalidConstantPoolIndex,
													CodeParsingException
	{
		className = inClass;
		accessFlags = dis.readUnsignedShort();
		nameIndex = dis.readUnsignedShort();
		descriptionIndex = dis.readUnsignedShort();
		attributes = new AttributeSet(dis, cp);
	}

	/// Helper function to resolve the return type of the descriptor type into 
	/// a string that can be returned and stored.
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
			/// Switches over 
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
	public String getClassName() { return className; }
	public AttributeSet getAttributeSet() { return attributes; }
}