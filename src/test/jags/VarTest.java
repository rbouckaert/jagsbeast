package test.jags;

import org.junit.Test;

import beast.app.beauti.BeautiDoc;
import beast.core.util.Log;
import jags.CalculatorListenerImpl;
import jags.CalculatorParsingException;
import jags.nodes.JFunction;
import junit.framework.TestCase;

/** tests for var_stmnt **/
public class VarTest extends TestCase {

	@Test
	public void testSimpleVarStatement() {
		BeautiDoc doc = new BeautiDoc();
		test("model{N=10}", doc);
		test("var a, b; model{c=3}", doc);
		
		assertEquals(4, doc.pluginmap.size());
		
		JFunction a = ((JFunction)doc.pluginmap.get("a"));
		assertEquals(1, a.getDimension());		
	}
	
	@Test
	public void testVarOneDimensinalStatement() {
		BeautiDoc doc = new BeautiDoc();
		test("model{N=10}", doc);
		doc = test("var a, b[N]; model{c=3}", doc);
		JFunction b = ((JFunction)doc.pluginmap.get("b"));
		assertEquals(10, b.getDimension());		
		assertEquals(1, b.getDimensionCount());		
		assertEquals(10, b.getDimension(0));		
	}

	@Test
	public void testVarTwoDimensinalStatement() {
		BeautiDoc doc = new BeautiDoc();
		test("model{N=10}", doc);
		doc = test("var a, b[N,N]; model{c=3}", doc);
		JFunction b = ((JFunction)doc.pluginmap.get("b"));
		assertEquals(100, b.getDimension());		
		assertEquals(2, b.getDimensionCount());		
		assertEquals(10, b.getDimension(0));		
		assertEquals(10, b.getDimension(1));		
	}

	@Test
	public void testVarTwoDimensinalStatement2() {
		BeautiDoc doc = new BeautiDoc();
		test("model{N=10}", doc);
		doc = test("var a, b[N-1,N]; model{c=3}", doc);
		JFunction b = ((JFunction)doc.pluginmap.get("b"));
		assertEquals(90, b.getDimension());		
		assertEquals(2, b.getDimensionCount());		
		assertEquals(9, b.getDimension(0));		
		assertEquals(10, b.getDimension(1));		
	}

	@Test
	public void testLoop2DimAssignment() {
		BeautiDoc doc = new BeautiDoc();
		test("model{N=3}", doc);
		String cmd = "var b[N,N]; model{"
				+ "a = c(c(3,2,1),c(30,20,10),c(300,200,100)) "
				+ "for (j in 1:3) {"
				+ "  for (i in 1:3) { "
				+ "    b[i,j] = a[j,i] "
				+ "  }"
				+ "}"
				+ "}";
		doc = test(cmd, doc);
		JFunction b = ((JFunction)doc.pluginmap.get("b"));
		assertEquals(9, b.getDimension());
		double [] expected = new double[] {3, 30, 300, 2, 20, 200, 1, 10, 100}; 
		for (int i = 0; i < 9; i++) {
			assertEquals(expected[i], b.getArrayValue(i));
		}
	}	

	BeautiDoc test(String cmd, BeautiDoc doc) {
		try {
			CalculatorListenerImpl parser = new CalculatorListenerImpl(doc);
			parser.parse(cmd);
			return doc;
		} catch (CalculatorParsingException e) {
			Log.info("model{" + cmd + "}");
			Log.info(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace(Log.err);
			Log.info("Error: " + e.getMessage());
		}
		return null;
	}
}
