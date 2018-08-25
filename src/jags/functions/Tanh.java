package jags.functions;

import jags.nodes.JFunction;

public class Tanh extends UnivariableFunction {

	public Tanh(JFunction x) {
		super(x);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			values[i] = Math.tanh(x.getArrayValue(i)); 
		}
	}

}
