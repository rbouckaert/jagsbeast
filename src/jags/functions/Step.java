package jags.functions;

import jags.nodes.JFunction;

public class Step extends UnivariableFunction {

	public Step(JFunction x) {
		super(x);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			values[i] = x.getArrayValue(i) > 0 ? 1 : 0; 
		}
	}

}
