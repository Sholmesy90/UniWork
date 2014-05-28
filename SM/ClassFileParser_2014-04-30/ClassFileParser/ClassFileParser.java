import java.io.*;
import java.util.*;

/**
 * Parses and displays a Java .class file.
 *
 * @author David Cooper
 */
public class ClassFileParser
{
    private static HashSet<String> methodCounter = new HashSet<String>();

    public static void main(String[] args)
    {
        if(args.length == 1)
        {
            try
            {
                /// Creates the initial classfile, as chosen by the user.

                ClassFile cf = new ClassFile(args[0]);

                System.out.println("\n");
                cf.getMethodSet().printList(cf.getConstantPool());
                
                System.out.println("\nSelect method to enter to produce call tree\n");
                Scanner in = new Scanner(System.in);
                int choice = -1;
                while (!(choice > 0 && choice <= cf.getMethodSet().getMethodCount()))
                {
                    choice = in.nextInt();
                }
                in.close();

                /// Builds a tree for this class file, building sub trees of other classes as it permutates
                /// down the tree.
                System.out.println("---------------------Tree Building Time---------------------\n");
                Method curMethod = cf.getMethodSet().getMethod(choice-1);
                TreeGenerator tg = new TreeGenerator(cf);
                System.out.println("Num Methods: " + tg.produceCallStructure(curMethod));
                System.out.println("Num Classes: " + tg.getClassCount());
                System.out.println("\n------------------------------------------------------------\n");


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
