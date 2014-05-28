public class TestClass
{
	private static final int testField = 2;

	public TestClass()
	{
		Method1(1,2);
		String[] s = {"1","2"};
		Method1(s);
		Method2("2");
	}
	public int Method1(int i, int j)
	{
		Method2("2");
		Method3('c');
		return 1;
	}

	public int Method1(String[] s)
	{
		return 1;
	}

	public void Method2(String s)
	{
		int[] a = new int[5];
		Method4(a);
		int x = 10;
	}

	public char Method3(char c)
	{
		return c;
	}
	public void Method4(int[] i)
	{
		
	}
}
