import java.io.*;

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
    }

    /** Returns the contents of the class file as a formatted String. */
    public String toString()
    {
        return String.format(
            "\nFilename: %s\n" +
            "Magic: 0x%08x\n" +
            "This class: " + tc  + "\nSuper class: " + sc +
            "\nAccess flags: 0x%04x\n" + 
            "Class file format version: %d.%d\n\n" +
            "Constant pool:\n\n%s" + "\n\nInterface information:\n\n%s" + 
            "\n\nField information:\n\n%s" + "\n\nMethod information:\n\n%s" + 
            "\n\nAttribute information:\n\n%s"

            ,filename, magic, accessFlags, majorVersion, minorVersion, constantPool,
            interfaceInfo, fieldInfo, methodInfo, attributeInfo);
    }
}

