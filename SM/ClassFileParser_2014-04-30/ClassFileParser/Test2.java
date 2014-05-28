/// This Class is responsible for testing the tree generator
/// for a test of a nested method call. Method1 calls Method2
public class Test2
{
	public Test2()
	{
		//super();
		Method1();
	}

	private void Method1()
	{
		Method2();
	}

	private void Method2()
	{

	}
}