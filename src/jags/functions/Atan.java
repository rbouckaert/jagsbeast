package jags.functions;

import jags.nodes.JFunction;

public class Atan extends UnivariableFunction {

	public Atan(JFunction x) {
		super(x);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			values[i] = Math.atan(x.getArrayValue(i)); 
		}
	}

}
