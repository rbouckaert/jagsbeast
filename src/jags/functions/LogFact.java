package jags.functions;

import jags.nodes.JFunction;

public class LogFact extends UnivariableFunction {

	public LogFact(JFunction x) {
		super(x);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			double arg = x.getArrayValue(i);			
			double logFactorial = 0;
			for (int j = 2; j <= arg; j++) {
			  logFactorial += Math.log(j);
			}
			values[i] = logFactorial;
		}
	}

}
