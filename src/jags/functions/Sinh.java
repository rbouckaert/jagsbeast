package jags.functions;

import jags.nodes.JFunction;

public class Sinh extends UnivariableFunction {

	public Sinh(JFunction x) {
		super(x);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			values[i] = Math.sinh(x.getArrayValue(i)); 
		}
	}

}
