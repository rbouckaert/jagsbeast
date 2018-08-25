package jags.functions;

import jags.nodes.JFunction;

public class Signum extends UnivariableFunction {

	public Signum(JFunction x) {
		super(x);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			values[i] = Math.signum(x.getArrayValue(i)); 
		}
	}

}
