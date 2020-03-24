package junit;

import junit.framework.TestCase;
import pass.DoDoWhile;


public class DoDoWhileTest extends TestCase{
	
	public void testDoWhile() {
		this.assertEquals(DoDoWhile.test1(), 2);
		this.assertEquals(DoDoWhile.test2(), 1);
	}
}