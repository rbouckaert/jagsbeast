package test.jags;

import org.junit.Test;

import beast.app.beauti.BeautiDoc;
import beast.core.util.Log;
import jags.CalculatorListenerImpl;
import jags.CalculatorParsingException;
import jags.nodes.JFunction;
import junit.framework.TestCase;

public class LoopTest extends TestCase {

	
	@Test
	public void testLoop() {
		String cmd = "a0 = c(3,2,1) b = c(1,2,3) for (i in 1:3) { a0[i] = b[i] }";
		assertEquals(1.0, test(cmd + " a = a0[1]"));
		assertEquals(2.0, test(cmd + " a = a0[2]"));
		assertEquals(3.0, test(cmd + " a = a0[3]"));

		cmd = "a0 = c(3,2,1) b = c(1,2,3) for (i in 1:3) { a0[i] = b[i] * b[i] }";
		assertEquals(1.0, test(cmd + " a = a0[1]"));
		assertEquals(4.0, test(cmd + " a = a0[2]"));
		assertEquals(9.0, test(cmd + " a = a0[3]"));

		cmd = "a0 = c(3,2,1) b = c(1,2,3) for (j in 1:3) {for (i in 1:3) { a0[i] = a0[i] + b[j] }}";
		assertEquals(9.0, test(cmd + " a = a0[1]"));
		assertEquals(8.0, test(cmd + " a = a0[2]"));
		assertEquals(7.0, test(cmd + " a = a0[3]"));
	}
	
	
	protected static double test(String cmd) {
		try {
			BeautiDoc doc = new BeautiDoc();
			CalculatorListenerImpl parser = new CalculatorListenerImpl(doc);
			parser.parse("model{" + cmd + "}");
			JFunction a = (JFunction) doc.pluginmap.get("a");
			return a.getArrayValue();
			//parser.parse(cmd);
		} catch (CalculatorParsingException e) {
			Log.info("model{" + cmd + "}");
			Log.info(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace(Log.err);
			Log.info("Error: " + e.getMessage());
		}
		return Double.NaN;
	}

}
