import java.io.*;

public class FieldSet
{
	/// Stores a set of field 
	private Field[] fieldArray;
	private int fieldsCount;

	public FieldSet(DataInputStream dis, ConstantPool cp) throws IOException, 
													InvalidConstantPoolIndex,
													CodeParsingException
	{
		fieldsCount = dis.readUnsignedShort();
		fieldArray = new Field[fieldsCount];
		for (int i = 0; i < fieldsCount; i++)
		{
			fieldArray[i] = new Field(dis, cp);
		}
	}
}