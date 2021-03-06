import java.io.*;
import java.util.*;

/// CodeAttribute inherits from AbstractAttribute and has a list of instruction
/// that it generates in the helper method CreateInstructionList().
/// This list of instructions is used to parse through the methods to
/// determine the final call structure of the class.
public class CodeAttribute extends AbstractAttribute
{
	private int maxStack;
	private int maxLocals;	
	private long codeLength;
	private byte[] code;
	private int exceptionTableLength;
	private byte[] exceptionTable;
	private AttributeSet attributeInfo;
	private ArrayList<Instruction> insList;

	public CodeAttribute(DataInputStream dis, int i, ConstantPool cp) throws IOException,
																InvalidConstantPoolIndex,
																CodeParsingException
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
		CreateInstructionList();
	}

	/// Populates the instruction list with instructions.
	public ArrayList<Instruction> CreateInstructionList() throws CodeParsingException
	{
		int curOffSet = 0;
		insList = new ArrayList<Instruction>();
		do 
		{
			Instruction newInst = new Instruction(code, curOffSet);
			curOffSet += newInst.getSize();
			insList.add(newInst);
		} 
		while (curOffSet < code.length);
		return insList;
	}

	public ArrayList<Instruction> getInstructList() { return insList; }
}
