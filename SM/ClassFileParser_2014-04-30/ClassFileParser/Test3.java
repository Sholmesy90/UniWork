/// This Class is responsible for testing the tree generator
/// for a simple recursive function. Method1 calls Method2 
/// which calls Method1
public class Test3
{
	public Test3()
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
		Method1();
	}
}