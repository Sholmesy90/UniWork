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
			String parameters = temp.resolveType(((ConstantUtf8)cp.getEntry(temp.getDescIndex())).getBytes());
			int menu = 1 + i;
			System.out.printf("(" + menu + ") " + "%s(" + parameters + ")\n", ((ConstantUtf8)cp.getEntry(temp.getNameIndex())).getBytes());
		}
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

	public int getMethodCount() { return methodCount; }
	public Method getMethod(int index) { return methodArray[index]; }
}