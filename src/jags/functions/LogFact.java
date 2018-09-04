package jags.functions;

import jags.nodes.JFunction;
import beast.core.Param;

import beast.core.Description;

@Description("Performs the LogFact function")
public class LogFact extends UnivariableFunction {

	public LogFact(@Param(name="x", description="function or value argument") JFunction x) {
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
