package jags.functions;

import jags.nodes.JFunction;

public class Logit extends UnivariableFunction {

	public Logit(JFunction x) {
		super(x);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			double arg = x.getArrayValue(i);
			values[i] = Math.log(arg) - Math.log(1 - arg);
		}
	}

}
