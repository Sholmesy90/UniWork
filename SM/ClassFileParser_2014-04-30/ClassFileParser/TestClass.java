public class TestClass
{
	private static final int testField = 2;

	public TestClass()
	{
		Method1(1);
		Method2();
	}
	public int Method1(int i)
	{
		Method2();
		Method3('c');
		return 1;
	}

	public void Method2()
	{
		int x = 10;
	}

	public char Method3(char c)
	{
		return c;
	}
}
