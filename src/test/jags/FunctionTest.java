package test.jags;

import org.apache.commons.math.MathException;
import org.apache.commons.math.special.Gamma;
import org.junit.Test;

import beast.app.beauti.BeautiDoc;
import beast.core.util.Log;
import jags.CalculatorListenerImpl;
import jags.CalculatorParsingException;
import jags.nodes.JFunction;

import static java.lang.Math.*;

import junit.framework.TestCase;


public class FunctionTest extends TestCase {

	@Test
	public void testMatrixFunctions() {
		String m1 = "m1=c(c(1,2),c(3,4))";
		String m2 = "m2=c(c(2,1),c(4,3))";
		String m3 = "m3=c(c(2,1,0),c(4,3,2))";

		assertEquals(test(m1 + " a=dim(m1)[1]"), 2.0);
		assertEquals(test(m1 + " a=dim(m1)[2]"), 2.0);
		assertEquals(test(m3 + " a=dim(m3)[1]"), 2.0);
		assertEquals(test(m3 + " a=dim(m3)[2]"), 3.0);
		
		assertEquals(test(m1 + " a=length(m1)"), 4.0);
		assertEquals(test(m3 + " a=length(m3)"), 6.0);

		assertEquals(test(m1 + " a=length(dim(m1))"), 2.0);
		assertEquals(test(m3 + " a=length(dim(m3))"), 2.0);

		assertEquals(test(m1 +" a= inverse(m1)[1]"), -2.0, 1e-12);
		assertEquals(test(m1 +" a= inverse(m1)[2]"), 1.5, 1e-12);
		assertEquals(test(m1 +" a= inverse(m1)[3]"), 1.0, 1e-12);
		assertEquals(test(m1 +" a= inverse(m1)[4]"), -0.5, 1e-12);
		
		assertEquals(test(m1 + " " + m2 +" a= inprod(m1,m2)[1]"), 2.0);
		assertEquals(test(m1 + " " + m2 +" a= inprod(m1,m2)[2]"), 2.0);
		assertEquals(test(m1 + " " + m2 +" a= inprod(m1,m2)[3]"), 12.0);
		assertEquals(test(m1 + " " + m2 +" a= inprod(m1,m2)[4]"), 12.0);
		
		assertEquals(test(m1 + " " + m2 +" a= prod(m1,m2)[1]"), 10.0);
		assertEquals(test(m1 + " " + m2 +" a= prod(m1,m2)[2]"), 7.0);
		assertEquals(test(m1 + " " + m2 +" a= prod(m1,m2)[3]"), 22.0);
		assertEquals(test(m1 + " " + m2 +" a= prod(m1,m2)[4]"), 15.0);

		assertEquals(test(m1 +" a=t(m1)[1]"), 1.0);
		assertEquals(test(m1 +" a=t(m1)[2]"), 3.0);
		assertEquals(test(m1 +" a=t(m1)[3]"), 2.0);
		assertEquals(test(m1 +" a=t(m1)[4]"), 4.0);
		
		assertEquals(test(m2 + " a= logdet(m2)"), log(2.0));
		//assertEquals(test(m1 + " " + m2 +" a= m1 %*% m2"), 1.0);
	}
	
	@Test
	public void testBivariableFunctions() {
		assertEquals(test("a=max(1.0,4.0)"), 4.0);
		assertEquals(test("a=max(4.0,1.0)"), 4.0);
		assertEquals(test("a=min(1.0,4.0)"), 1.0);
		assertEquals(test("a=min(4.0,1.0)"), 1.0);
		
		assertEquals(test("a=hypot(1.0,1.0)"), sqrt(2));
		assertEquals(test("a=atan2(1.0,1.0)"), PI/4);
		assertEquals(test("a=pow(2.0,4.0)"), 16.0);
		
		assertEquals(test("a=equals(2.0,2.0)"), 1.0);
		assertEquals(test("a=equals(2.0,-2.0)"), 0.0);
		
		assertEquals(test("a=min(1.0,4.0)"), 1.0);
	}
	

	@Test
	public void testVectorFunctions() {
		String s = "c(2.0, 4.0, 1.0, 3.0)";
		
		assertEquals(test("a=max(" + s + ")"), 4.0);
		assertEquals(test("a=min(" + s + ")"), 1.0);
		assertEquals(test("a=mean(" + s + ")"), 2.5);
		assertEquals(test("a=sd(" + s + ")"), 1.2909944487358056);
		assertEquals(test("a=sum(" + s + ")"), 10.0);

		assertEquals(test("a=sort(" + s + ")"), 1.0);
		assertEquals(test("a=order(" + s + ")"), 3.0);
		assertEquals(test("a=rank(" + s + ")"), 2.0);
	}

	@Test
	public void testUnaryFunctions() {
		testUnaryFunctions("0.0", 0.0);
		testUnaryFunctions("0.75", 0.75);
		testUnaryFunctions("-0.75", -0.75);		
	}

	public void testUnaryFunctions(String v, double d) {
		assertEquals(test("a=abs(" + v + ")"), abs(d));

		assertEquals(test("a=cbrt(" + v + ")"), cbrt(d));
		assertEquals(test("a=cloglog(" + v + ")"), log(-log(1 - d)));
		assertEquals(test("a=sqrt(" + v + ")"), sqrt(d));
		assertEquals(test("a=exp(" + v + ")"), exp(d));
		assertEquals(test("a=expm1(" + v + ")"), expm1(d));
		assertEquals(test("a=log(" + v + ")"), log(d));
		assertEquals(test("a=log10(" + v + ")"), log10(d));
		assertEquals(test("a=log1p(" + v + ")"), log1p(d));
		assertEquals(test("a=loggamm(" + v + ")"), Gamma.logGamma(d));
		assertEquals(test("a=logit(" + v + ")"), log(d) - log(1 - d));
		assertEquals(test("a=logfact(" + v + ")"), logfact(d));
		try {
			if (d >= 0 && d <= 1) {
				assertEquals(test("a=probit(" + v + ")"), sqrt(2) *  org.apache.commons.math.special.Erf.erf(2*d - 1));
			}
		} catch (MathException e) {
			// ignore invalid input
		}
		assertEquals(test("a=ceil(" + v + ")"), ceil(d));
		assertEquals(test("a=trunc(" + v + ")"), floor(d));
		assertEquals(test("a=floor(" + v + ")"), floor(d));
		assertEquals(test("a=round(" + v + ")"), (double) round(d));
		assertEquals(test("a=signum(" + v + ")"), signum(d));
		assertEquals(test("a=step(" + v + ")"), (double)(d > 0 ? 1 : 0));
	}

	
	private double logfact(double d) {
		double logFactorial = 0;
		for (int j = 2; j <= d; j++) {
		  logFactorial += log(j);
		}
		return logFactorial;
	}

	
	@Test
	public void testTrigeomatryFunctions() {
		testTrigeomatryFunctions("0.0", 0.0);
		testTrigeomatryFunctions("0.5", 0.5);
		testTrigeomatryFunctions("-0.5", -0.5);
		testTrigeomatryFunctions("0.25", 0.25);
		testTrigeomatryFunctions("-0.25", -0.25);
	}
	
	public void testTrigeomatryFunctions(String v, double d) {
		assertEquals(test("a=sin(" + v + ")"), sin(d));
		assertEquals(test("a=cos(" + v + ")"), cos(d));
		assertEquals(test("a=tan(" + v + ")"), tan(d));
		
		assertEquals(test("a=asin(" + v + ")"), asin(d));
		assertEquals(test("a=acos(" + v + ")"), acos(d));
		assertEquals(test("a=atan(" + v + ")"), atan(d));

		assertEquals(test("a=arcsin(" + v + ")"), asin(d));
		assertEquals(test("a=arccos(" + v + ")"), acos(d));
		assertEquals(test("a=arctan(" + v + ")"), atan(d));

		assertEquals(test("a=sinh(" + v + ")"), sinh(d));
		assertEquals(test("a=cosh(" + v + ")"), cosh(d));
		assertEquals(test("a=tanh(" + v + ")"), tanh(d));

		assertEquals(test("a=asinh(" + v + ")"), asinh(d));
		assertEquals(test("a=acosh(" + v + ")"), acosh(d));
		assertEquals(test("a=atanh(" + v + ")"), atanh(d));
		
		assertEquals(test("a=arcsinh(" + v + ")"), asinh(d));
		assertEquals(test("a=arccosh(" + v + ")"), acosh(d));
		assertEquals(test("a=arctanh(" + v + ")"), atanh(d));
	}
	
	double asinh(double val) {return (Math.log(val + Math.sqrt(val * val + 1)));}
	double acosh(double val) {return (Math.log(val + Math.sqrt(val + 1) * Math.sqrt(val - 1)));}
	double atanh(double val) {return (0.5 * Math.log((1 + val) / (1 - val)));} 
	
	private double test(String cmd) {
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
