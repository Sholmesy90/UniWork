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