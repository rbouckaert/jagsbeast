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
	}

	@Test
	public void testVarTwoeDimensinalStatement() {
		BeautiDoc doc = new BeautiDoc();
		test("model{N=10}", doc);
		doc = test("var a, b[N,N]; model{c=3}", doc);
		JFunction b = ((JFunction)doc.pluginmap.get("b"));
		assertEquals(100, b.getDimension());		
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