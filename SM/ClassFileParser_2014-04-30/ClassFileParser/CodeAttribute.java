import java.io.*;

public class CodeAttribute extends AbstractAttribute
{
	private int maxStack;
	private int maxLocals;	
	private long codeLength;
	private byte[] code;
	private int exceptionTableLength;
	private byte[] exceptionTable;
	private AttributeSet attributeInfo;

	public CodeAttribute(DataInputStream dis, int i, ConstantPool cp) throws IOException,
																InvalidConstantPoolIndex
	{
		attributeNameIndex = i;
		attributeLength = (long)dis.readUnsignedShort() << 16 | dis.readUnsignedShort();
		maxStack = dis.readUnsignedShort();
		maxLocals = dis.readUnsignedShort();
		codeLength = (long)dis.readUnsignedShort() << 16 | dis.readUnsignedShort();
		code = new byte[(int)codeLength];
		dis.readFully(code);
		exceptionTableLength = dis.readUnsignedShort();
		exceptionTable = new byte[exceptionTableLength*8];
		dis.readFully(exceptionTable);
		attributeInfo = new AttributeSet(dis, cp);
	}
}