package jags.functions;

import jags.nodes.JFunction;

public class Tan extends UnivariableFunction {

	public Tan(JFunction x) {
		super(x);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			values[i] = Math.tan(x.getArrayValue(i)); 
		}
	}

}
