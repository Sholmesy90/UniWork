import java.io.*;

/**
 * Parses and displays a Java .class file.
 *
 * @author David Cooper
 */
public class ClassFileParser
{
    public static void main(String[] args)
    {
        if(args.length == 1)
        {
            try
            {
                ClassFile cf = new ClassFile(args[0]);
                System.out.println(cf);
            }
            catch(IOException e)
            {
                System.out.printf("Cannot read \"%s\": %s\n",
                    args[0], e.getMessage());
            }
            catch(ClassFileParserException e)
            {
                System.out.printf("Class file format error in \"%s\": %s\n",
                    args[0], e.getMessage());
            }
        }
        else
        {
            System.out.println("Usage: java ClassFileParser <class-file>");
        }
    }
}
