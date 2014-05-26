import java.io.*;

public class FieldSet
{
	private Field[] fieldArray;
	private int fieldsCount;

	public FieldSet(DataInputStream dis, ConstantPool cp) throws IOException, 
													InvalidConstantPoolIndex
	{
		fieldsCount = dis.readUnsignedShort();
		fieldArray = new Field[fieldsCount];
		for (int i = 0; i < fieldsCount; i++)
		{
			fieldArray[i] = new Field(dis, cp);
		}
	}

	public String toString()
	{
		String s =  "Num of fields: " + fieldsCount + "\n" +
					"Flags  NameIdx  DescIdx\n" +
				    "-----------------------\n";
		for (int i = 0; i < fieldsCount; i++)
		{
			s += fieldArray[i];
		}
		return s;
	}
}