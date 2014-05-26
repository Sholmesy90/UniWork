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
    private int interfacesCount;
    private int[] interfaces;
    private int fieldsCount;
    private int attributesCount;
    private Attribute[] attributeInfo;
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
        interfacesCount = dis.readUnsignedShort();
        interfaces = new int[interfacesCount];
        for (int i = 0; i < interfacesCount; i++)
        {
        	interfaces[i] = dis.readUnsignedShort();
        }      
        
        fieldsCount = dis.readUnsignedShort();
        
        attributesCount = dis.readUnsignedShort();
        for (int i = 0; i < attributesCount; i++)
        {
        	attributeInfo[i] = new Attribute(dis);
        }



        tc = ((ConstantUtf8)constantPool.getEntry(
            ((ConstantClass)constantPool.getEntry(thisClass)).getNameIndex()
            )).getBytes();
    	sc = ((ConstantUtf8)constantPool.getEntry(
            ((ConstantClass)constantPool.getEntry(superClass)).getNameIndex()
            )).getBytes();;
    }

    /** Returns the contents of the class file as a formatted String. */
    public String toString()
    {
        return String.format(
            "Filename: %s\n" +
            "Magic: 0x%08x\n" +
            "Class file format version: %d.%d\n\n" +
            "Constant pool:\n\n%s" + "Access flags: 0x%04x\n" +
            "This class: " + tc  + "\nSuper class: " + sc +
            "\nNum interfaces: %d\n",
            filename, magic, majorVersion, minorVersion, constantPool,
            accessFlags, interfacesCount);
    }
}

