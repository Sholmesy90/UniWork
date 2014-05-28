/// This Class is responsible for testing the tree generator
/// for a triple method recursion. Method1->Method2->Method3->Method1
public class Test4
{
	public Test4()
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
		Method3();
	}

	private void Method3()
	{
		Method1();
	}
}