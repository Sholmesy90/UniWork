import java.io.*;
import java.util.*;

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
		CreateMethodRefList(CreateInstructionList(), cp);
	}

	public ArrayList<Instruction> CreateInstructionList() throws CodeParsingException
	{
		int curOffSet = 0;
		ArrayList<Instruction> insList = new ArrayList<Instruction>();
		do 
		{
			Instruction newInst = new Instruction(code, curOffSet);
			curOffSet += newInst.getSize();
			insList.add(newInst);
		} 
		while (curOffSet < code.length);
		return insList;
	}

	public ArrayList<ConstantMethodRef> CreateMethodRefList(
		ArrayList<Instruction> inList, ConstantPool cp) throws InvalidConstantPoolIndex
	{
		ArrayList<ConstantMethodRef> methodList = new ArrayList<ConstantMethodRef>();
		for (int i = 0; i < inList.size(); i++)
		{	
			if   ((inList.get(i).getOpcode().getMnemonic().equals("invokestatic"))
				||(inList.get(i).getOpcode().getMnemonic().equals("invokevirtual"))
				||(inList.get(i).getOpcode().getMnemonic().equals("invokeinterface"))
				||(inList.get(i).getOpcode().getMnemonic().equals("invokespecial")))
			{


				byte[] bytes = inList.get(i).getExtraBytes();
				int combination = bytes[0] << 8 | bytes[1];

				ConstantMethodRef methodRef = (ConstantMethodRef)cp.getEntry(combination);
				methodList.add(methodRef);
			}
		}
		return methodList;
	}
}