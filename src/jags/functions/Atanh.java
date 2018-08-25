package jags.functions;

import jags.nodes.JFunction;

public class Atanh extends UnivariableFunction {

	public Atanh(JFunction x) {
		super(x);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			double val = x.getArrayValue(i);
			values[i] = (0.5 * Math.log((1 + val) / (1 - val)));
		}
	}

}
