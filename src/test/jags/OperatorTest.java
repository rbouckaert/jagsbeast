package test.jags;

import org.junit.Test;

import junit.framework.TestCase;

public class OperatorTest extends TestCase {

	@Test
	public void testOperator() {
		testOperator("0", "1", 0, 1);
		testOperator("1", "1", 1, 1);
		testOperator("1", "0", 1, 0);
		testOperator("2", "2", 2, 2);
	}
	
	void testOperator(String a, String b, double av, double bv) {
		
		if (av <= bv) {
			assertEquals(test("a=" + a + ":" + b),  (double)(int) av);
		}
		assertEquals(test("a=" + a + "^" + b),  (double)((int) av ^ (int) bv ));
		
		assertEquals(test("a=" + a + "+" + b), av+bv);
		assertEquals(test("a=" + a + "-" + b), av-bv);
		assertEquals(test("a=" + a + "*" + b), av*bv);
		assertEquals(test("a=" + a + "**" + b), Math.pow(av, bv));
		assertEquals(test("a=" + a + "&&" + b), (double)(av> 0 && bv > 0 ? 1 : 0));
		assertEquals(test("a=" + a + "||" + b), (double)(av> 0 || bv > 0 ? 1 : 0));
		assertEquals(test("a=" + a + "<=" + b), (double)( av <= bv ? 1 : 0));
		assertEquals(test("a=" + a + "<" + b),  (double)(av < bv ? 1 : 0));
		assertEquals(test("a=" + a + ">=" + b), (double)(av >= bv ? 1 : 0));
		assertEquals(test("a=" + a + ">" + b),  (double)(av > bv ? 1 : 0));
		assertEquals(test("a=" + a + "!=" + b), (double)(av != bv ? 1 : 0));
		assertEquals(test("a=" + a + "==" + b), (double)(av == bv ? 1 : 0));
		assertEquals(test("a=" + a + "&" + b),  (double)((int) av & (int) bv ));
		assertEquals(test("a=" + a + "|" + b),  (double)((int) av | (int) bv ));
		assertEquals(test("a=" + a + "%" + b),  (double)(av % bv ));
		
		assertEquals(test("a=" + a + ">>" + b), (double)((int) av >> (int) bv ));
		assertEquals(test("a=" + a + "<<" + b), (double)((int) av << (int) bv ));
		assertEquals(test("a=" + a + ">>>" + b),(double)((int) av >>> (int) bv ));
	}

	double test(String cmd) {
		return FunctionTest.test(cmd);
	}
}
