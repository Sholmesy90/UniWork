public class TestClass
{
	private static final int testField = 2;
	public String testField2;

	public int Method1(int x1, int x2)
	{
		return x1 + x2;
	}

	public void Method2()
	{
		int x = 10;
	}

	private String Method3(char[] c)
	{
		String s = "";
		for (int i = 0; i < c.length; i++)
		{
			s += c;
		}
		return s;
	}
}
