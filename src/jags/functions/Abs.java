package jags.functions;

import jags.nodes.JFunction;

public class Abs extends UnivariableFunction {

	public Abs(JFunction x) {
		super(x);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			values[i] = Math.abs(x.getArrayValue(i)); 
		}
	}

}
