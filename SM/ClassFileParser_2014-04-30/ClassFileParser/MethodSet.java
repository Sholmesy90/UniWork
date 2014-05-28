import java.io.*;

public class MethodSet
{
	/// Stores a set of methods
	private Method[] methodArray;
	private int methodCount;

	public MethodSet(DataInputStream dis, ConstantPool cp, String inClass) throws IOException,
													InvalidConstantPoolIndex,
													CodeParsingException
	{
		methodCount = dis.readUnsignedShort();
		methodArray = new Method[methodCount];
		for (int i = 0; i < methodCount; i++)
		{
			methodArray[i] = new Method(dis, cp, inClass);
		}
	}

	/// Print list is used to display all the available enterable methods to the user.
	public void printList(ConstantPool cp) throws InvalidConstantPoolIndex
	{
		for (int i = 0; i < methodCount; i++)
		{
			Method temp = methodArray[i];
			String parameters = temp.resolveType(((ConstantUtf8)cp.getEntry(temp.getDescIndex())).getBytes());
			int menu = 1 + i;
			System.out.printf("(" + menu + ") " + temp.getClassName() + ".%s(" + parameters + ")\n", ((ConstantUtf8)cp.getEntry(temp.getNameIndex())).getBytes());
		}
	}
	
	public int getMethodCount() { return methodCount; }
	public Method getMethod(int index) { return methodArray[index]; }
}