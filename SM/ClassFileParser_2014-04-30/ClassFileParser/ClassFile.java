import java.io.*;
import java.util.*;

/**
 * Parses and stores a Java .class file. Parsing is currently incomplete.
 *
 * @author David Cooper
 */


/// This class stores all the information of each .class file that is loaded
/// into the function. There can be multiple instances of this class if another 
/// object is called within a current object.
public class ClassFile
{   
    /// Class fields
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
    private HashMap<String, Method> dictionary;

    /// Takes a file and extracts the class info. 
    /// Stores it in various data structures
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
        methodInfo = new MethodSet(dis, constantPool, filename.substring(0,filename.length()-6));
        attributeInfo = new AttributeSet(dis, constantPool);

        dictionary = new HashMap<String, Method>();

        /// Iterates through all the methods in this class and populates a dictionary/map with
        /// the key being the combination of hte classname, methodname and parameter/return type.
        for (int i = 0; i < methodInfo.getMethodCount(); i++)
        {
            Method temp = methodInfo.getMethod(i);
            String s = temp.getClassName() + "." + ((ConstantUtf8)constantPool.getEntry(
                        temp.getNameIndex())).getBytes() + "(" + temp.resolveType(((ConstantUtf8)constantPool.getEntry(
                        temp.getDescIndex())).getBytes()) + ")";
            dictionary.put(s, temp);
        }
    }
    
    public MethodSet getMethodSet() { return methodInfo; }
    public ConstantPool getConstantPool() { return constantPool; }
    public HashMap<String, Method> getDictionary() { return dictionary; }
    public String getClassName() { return filename.substring(0,filename.length()-6); }
}

