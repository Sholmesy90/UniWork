/// This Class is responsible for testing the tree generator
/// for all the previous 9 test cases.
public class TotalTester
{
	public TotalTester()
	{
		/// This class runs the 9 other test cases and builds a call tree
		/// from them to use to check that the tests are working correctly.
		//super();
		Test1 t1 = new Test1();
		Test2 t2 = new Test2();
		Test3 t3 = new Test3();
		Test4 t4 = new Test4();
		Test5 t5 = new Test5();
		Test6 t6 = new Test6();
		//Test7 t7 = new Test7(); Abstract
		Test8 t8 = new Test8();
		Test9 t9 = new Test9();
	}
}
