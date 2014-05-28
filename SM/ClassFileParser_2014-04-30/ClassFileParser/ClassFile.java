import java.io.*;
import java.util.Scanner;

/**
 * Parses and stores a Java .class file. Parsing is currently incomplete.
 *
 * @author David Cooper
 */
public class ClassFile
{
    private String filename;
    private long magic;
    private int minorVersion;
    private int majorVersion;
    private ConstantPool constantPool;
    private int accessFlags;
    private int thisClass;
    private int superClass;
    private InterfaceSet interfaceInfo;
    private FieldSet fieldInfo;
    private MethodSet methodInfo;
    private AttributeSet attributeInfo;
    private String tc;
    private String sc;

    public ClassFile(String filename) throws ClassFileParserException,
                                             IOException
    {
        DataInputStream dis =
            new DataInputStream(new FileInputStream(filename));

        this.filename = filename;
        magic = (long)dis.readUnsignedShort() << 16 | dis.readUnsignedShort();
        minorVersion = dis.readUnsignedShort();
        majorVersion = dis.readUnsignedShort();
        constantPool = new ConstantPool(dis);
        accessFlags = dis.readUnsignedShort();
        thisClass = dis.readUnsignedShort();
        superClass = dis.readUnsignedShort();

        interfaceInfo = new InterfaceSet(dis);
        fieldInfo = new FieldSet(dis, constantPool);
        methodInfo = new MethodSet(dis, constantPool);
        attributeInfo = new AttributeSet(dis, constantPool);

        tc = ((ConstantUtf8)constantPool.getEntry(
            ((ConstantClass)constantPool.getEntry(thisClass)).getNameIndex()
            )).getBytes();
    	sc = ((ConstantUtf8)constantPool.getEntry(
            ((ConstantClass)constantPool.getEntry(superClass)).getNameIndex()
            )).getBytes();
        produceCallStructure();
    }

    /** Returns the contents of the class file as a formatted String. */
    public String toString()
    {
        return String.format(
            "\nFilename: %s\n" +
            "Magic: 0x%08x\n" +
            "This class: %s"   + "\nSuper class: %s" +
            "\nAccess flags: 0x%04x\n" + 
            "Class file format version: %d.%d\n\n" +
            "Constant pool:\n\n%s" + "\n\nInterface information:\n\n%s" + 
            "\n\nField information:\n\n%s" + "\n\nMethod information:\n\n%s" + 
            "\n\nAttribute information:\n\n%s"
            ,filename, magic, tc, sc, accessFlags, majorVersion, minorVersion, constantPool,
            interfaceInfo, fieldInfo, methodInfo, attributeInfo);
    }

    private void produceCallStructure() throws InvalidConstantPoolIndex
    {
        System.out.println("\n");
        methodInfo.printList(constantPool);
        
        
        System.out.println("\nSelect method to enter to produce call tree\n");
        Scanner in = new Scanner(System.in);
        int choice = -1;
        while (!(choice > 0 && choice <= methodInfo.getMethodCount()))
        {
            choice = in.nextInt();
        }
        in.close();
        /// Enter methodInfo[choice]
        /// For the code attribnute find other invoke's
        Method curMethod = methodInfo.getMethod(choice-1);
        System.out.println("\n\n");
        recurseCall(curMethod);
    }

    private void recurseCall(Method curMethod) throws InvalidConstantPoolIndex
    {
        System.out.println("" + ((ConstantUtf8)constantPool.getEntry(
                        curMethod.getNameIndex())).getBytes() + "("
                        + curMethod.resolveType(((ConstantUtf8)constantPool.getEntry(
                            curMethod.getDescIndex())).getBytes()) + ")");
        AttributeSet curAttributes = curMethod.getAttributeSet();
        for (int i = 0; i < curAttributes.getCount(); i++)
        {
            if (curAttributes.getAttribute(i) instanceof CodeAttribute)
            {
                CodeAttribute code = (CodeAttribute)curAttributes.getAttribute(i);
                for (int j = 0; j < code.getInstructList().size(); j++)
                {   
                    Instruction inst = code.getInstructList().get(j);
                    if   ((inst.getOpcode().getMnemonic().equals("invokestatic"))
                        ||(inst.getOpcode().getMnemonic().equals("invokevirtual"))
                        ||(inst.getOpcode().getMnemonic().equals("invokeinterface"))
                        ||(inst.getOpcode().getMnemonic().equals("invokespecial")))
                    {
                        byte[] bytes = inst.getExtraBytes();
                        int combination = bytes[0] << 8 | bytes[1];

                        ConstantMethodRef methodRef = (ConstantMethodRef)constantPool.getEntry(combination);
        
                        recurseCall(findMethod(methodRef));
                    }
                }
            }
        }
    }

    private Method findMethod(ConstantMethodRef methodRef) throws InvalidConstantPoolIndex
    {
        Method temp = methodInfo.getMethod(0);
        for (int i = 0; i < methodInfo.getMethodCount(); i++)
        {
            temp = methodInfo.getMethod(i);
            int nIndx = temp.getNameIndex();
            int tIndx = temp.getDescIndex();
            String name = ((ConstantUtf8)constantPool.getEntry(nIndx)).getBytes();
            String type = ((ConstantUtf8)constantPool.getEntry(tIndx)).getBytes();
            type = temp.resolveType(type);

            String comp1 = (name + "(" + type + ")");

            String name2 = methodRef.getName();
            String type2 = methodRef.getType();
            type2 = temp.resolveType(type2);

            String comp2 = (name2 + "(" + type2 + ")");
            
            if (comp1.equals(comp2))
            {
                i = methodInfo.getMethodCount();
            }
        }
        return temp;
    }
}

