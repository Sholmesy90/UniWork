import java.io.*;

public class MethodSet
{
	private Method[] methodArray;
	private int methodCount;

	public MethodSet(DataInputStream dis, ConstantPool cp) throws IOException,
													InvalidConstantPoolIndex,
													CodeParsingException
	{
		methodCount = dis.readUnsignedShort();
		methodArray = new Method[methodCount];
		for (int i = 0; i < methodCount; i++)
		{
			methodArray[i] = new Method(dis, cp);
		}
	}

	public void printList(ConstantPool cp) throws InvalidConstantPoolIndex
	{
		for (int i = 0; i < methodCount; i++)
		{
			Method temp = methodArray[i];
			String type = resolveType(((ConstantUtf8)cp.getEntry(temp.getDescIndex())).getBytes());
			System.out.printf("%s %s\n",
				((ConstantUtf8)cp.getEntry(temp.getNameIndex())).getBytes(),
				((ConstantUtf8)cp.getEntry(temp.getDescIndex())).getBytes());
		}
	}

	private String resolveType(String s)
	{
		//Split the string from (V)V into V & V.
		String[] stringArray = s.split("\\(");
		String[] stringArray2 = stringArray[1].split("\\)");
		String[] returnArray = new String[2];

		for (int i = 0; i < 2; i ++)
		{
			switch (stringArray2[i].charAt(0))
			{
				case 'B':
					returnArray[i] = "byte";
					break;
				case 'C':
					returnArray[i] = "char";
					break;
				case 'D':
					returnArray[i] = "double";
					break;
				case 'F':
					returnArray[i] = "float";
					break;
				case 'I':
					returnArray[i] = "int";
					break;
				case 'J':
					returnArray[i] = "long";
					break;
				case 'L':
					//returnArray[i] = HandleObject(stringArray2[i]);
					break;
				case 'S':
					returnArray[i] = "short";
					break;
				case 'Z':
					returnArray[i] = "byte";
					break;
				case '[':
					returnArray[i] = "[]";
			}
		}

		System.out.println("(" +stringArray2[0] + ") " + stringArray2[1]);
		return "";
	}

	public String toString()
	{
		String s =  "Num of Methods: " + methodCount + "\n" +
					"Flags  NameIdx  DescIdx\n" +
				    "-----------------------\n";
		for (int i = 0; i < methodCount; i++)
		{
			s += methodArray[i];
		}
		return s;
	}
}