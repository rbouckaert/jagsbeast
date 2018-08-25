package jags.functions;

import org.apache.commons.math.special.Gamma;

import jags.nodes.JFunction;

public class LogGamma extends UnivariableFunction {

	public LogGamma(JFunction x) {
		super(x);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			double val = x.getArrayValue(i);
			values[i] = Gamma.logGamma(val);					
		}
	}

}
