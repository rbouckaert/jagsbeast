package jags.functions;

import jags.nodes.JFunction;

public class Asinh extends UnivariableFunction {

	public Asinh(JFunction x) {
		super(x);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			double val = x.getArrayValue(i);
			values[i] = (Math.log(val + Math.sqrt(val * val + 1)));					
		}
	}

}
