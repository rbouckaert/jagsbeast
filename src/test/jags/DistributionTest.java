package test.jags;

import org.junit.Test;

import beast.app.beauti.BeautiDoc;
import beast.core.util.Log;
import jags.CalculatorListenerImpl;
import jags.CalculatorParsingException;
import jags.nodes.*;
import junit.framework.TestCase;

public class DistributionTest extends TestCase {

	@Test
	public void testNormal() {
		assertEquals(test("a = c(0.5, 0.5)  a ~ ddirich(c(1,1))"), 8.881784197001252E-16);
		assertEquals(test("a = 1;  a ~ dnorm(0,1)"), -1.4189385332046727);
		assertEquals(test("a = 0.2  a ~ dbeta(2,3)"), 0.42918163472548);
		assertEquals(test("a = 1  a ~ dexp(1)"), -1.0);
		assertEquals(test("a = c(0.5, 0.5)  a ~ dlnorm(1, 1)"), -3.318330080327547);
		assertEquals(test("a = 0.5  a ~ dlnorm(1, 1)"), -3.318330080327547/2);
	}

	@Test
	public void testLoopWithDistr() {
		String cmd = "a0 = c(1,2,3) for (i in 1:3) { a0[i] ~ dnorm(0,1)}";
		assertEquals(test(cmd, "logP.a0[1]"), -1.4189385332046727);
		assertEquals(test(cmd, "logP.a0[2]"), -2.9189385332046727);
		assertEquals(test(cmd, "logP.a0[3]"), -5.418938533204672);
	}

	protected static double test(String cmd) {
		return test(cmd, "logP.a");
	}
	
	protected static double test(String cmd, String valueOfInterest) {
		try {
			BeautiDoc doc = new BeautiDoc();
			CalculatorListenerImpl parser = new CalculatorListenerImpl(doc);
			parser.parse("model{" + cmd + "}");
			Distribution a = (Distribution) doc.pluginmap.get(valueOfInterest);
			double logP = a.calculateLogP();
			return logP;
			//parser.parse(cmd);
		} catch (CalculatorParsingException e) {
			Log.info("model{" + cmd + "}");
			Log.info(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace(Log.err);
			Log.info("Error: " + e.getMessage());
		}
		return Double.NaN;
	}}
