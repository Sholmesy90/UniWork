import java.io.*;
import java.util.*;

/// TreeGenerator is where the recursive function finding happens. 
/// It takes an initial method, that was selected by either the user
/// or one of the classes that is invoked from the user. It then finds
/// this method and determines if this method calls any other methods 
/// (recursively).

public class TreeGenerator
{
    /// Required fields.
	private int depth;
	private ClassFile classFile;
	private Stack<Method> recursiveStack;
	private int methodCounter = 0;
	private int classCounter = 0;

	public TreeGenerator(ClassFile cf)
	{
		classFile = cf;
	}

	public int produceCallStructure(Method curMethod) throws InvalidConstantPoolIndex
    {
    	recursiveStack = new Stack<Method>();
        recurseCall(curMethod);
        return methodCounter;
    }

    private void recurseCall(Method curMethod) throws InvalidConstantPoolIndex
    {
        String tabs = "";
        /// Adds a set of dashes to illustrate depth.
        for (int i = 0; i < depth; i++)
        {
            tabs += "----";
        }
        /// Resolves the current method into a string to be used for the comparison
        /// to determine if it has been called before (recursion). 
        String s = curMethod.getClassName() + "." + ((ConstantUtf8)
        				classFile.getConstantPool().getEntry(
                        curMethod.getNameIndex())).getBytes() + "("
                        + curMethod.resolveType(((ConstantUtf8)classFile.getConstantPool()
                        	.getEntry(curMethod.getDescIndex())).getBytes()) + ")";
        methodCounter++;
        System.out.println(tabs + "" + s);

        /// Gets the attributes out of the current method and determines if it is a 
        /// Code Attribute, that will potentially have a method call within it.
        AttributeSet curAttributes = curMethod.getAttributeSet();
        for (int i = 0; i < curAttributes.getCount(); i++)
        {
            /// Determines if is Code
            if (curAttributes.getAttribute(i) instanceof CodeAttribute)
            {
                CodeAttribute code = (CodeAttribute)curAttributes.getAttribute(i);
                for (int j = 0; j < code.getInstructList().size(); j++)
                {   
                    /// Determines if the Code is a method call or not.
                    Instruction inst = code.getInstructList().get(j);
                    if   ((inst.getOpcode().getMnemonic().equals("invokestatic"))
                        ||(inst.getOpcode().getMnemonic().equals("invokevirtual"))
                        ||(inst.getOpcode().getMnemonic().equals("invokeinterface"))
                        ||(inst.getOpcode().getMnemonic().equals("invokespecial")))
                    {
                        byte[] bytes = inst.getExtraBytes();
                        int combination = bytes[0] << 8 | bytes[1];

                        ConstantMethodRef methodRef = (ConstantMethodRef)classFile.getConstantPool()
                        								.getEntry(combination);

                        /// Checks if the class of the next method call is the same class as our current class
                        if (!(methodRef.getClassName().equals(classFile.getClassName())))
				        {
				        	try 
				        	{
				        		classCounter++;
                                /// Creates a new Classfile and tree for the new class
                                /// That will recurse creating a subtree.
				        		ClassFile cf = new ClassFile(methodRef.getClassName() +".class");
        						Method temp = cf.getMethodSet().getMethod(0);
				        		String checker = methodRef.getClassName() + "."
		                        				+ methodRef.getName() + "("
		                        				+ temp.resolveType(methodRef.getType()) + ")";
				        		temp = (cf.getDictionary()).get(checker);
				        		TreeGenerator tg2 = new TreeGenerator(cf);
				        		methodCounter += tg2.produceCallStructure(temp);
				        	}
				        	catch (IOException ex)
				        	{
				        		System.out.println(methodRef.getClassName() + "[-missing-]");
				        	}
				        	catch (ClassFileParserException ex)
				        	{
				        		ex.printStackTrace();
				        	}
				        }
				        else
				        {
	                        depth++; 
                            /// Using a stack to store the current methods we are in
                            /// to ensure no recursion occurs.
	                        recursiveStack.push(curMethod);
	                        Method newMethod = findMethod(methodRef);
	                        if (!((newMethod.getAccessFlags() & 1024) != 1024))
	                        {
	                        	System.out.println(tabs + "" + s + "[-abstract-]");
	                        }
	                        else
	                        {
		                        if (recursiveStack.contains(newMethod))
		                        {
		                            System.out.println(tabs + "" + s + "[-recursive-]");
		                        }
		                        else
		                        {  
		                            recurseCall(newMethod);
		                        }
		                    }
	                        recursiveStack.pop();
	                        depth--;
	                    }
                    }
                }
            }
        }
    }
    
    ///Resolves a method from a methodRef object.
    private Method findMethod(ConstantMethodRef methodRef) throws InvalidConstantPoolIndex
    {
        //Construct string from methodRef.
        Method temp = classFile.getMethodSet().getMethod(0);
        String checker = methodRef.getClassName() + "."
                        + methodRef.getName() + "("
                        + temp.resolveType(methodRef.getType()) + ")";
        //System.out.println(checker);
        temp = (classFile.getDictionary()).get(checker);
        return temp;
    }

    public int getClassCount() { return classCounter; }

}