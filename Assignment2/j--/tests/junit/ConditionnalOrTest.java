package junit;

import junit.framework.TestCase;
import pass.ConditionnalOr;


public class ConditionnalOrTest extends TestCase{
	
	public void testConditionnalOr() {
		this.assertEquals(ConditionnalOr.ff(), false);
		this.assertEquals(ConditionnalOr.ft(), true);
		this.assertEquals(ConditionnalOr.tf(), true);
		this.assertEquals(ConditionnalOr.tt(), true);
	}
}
