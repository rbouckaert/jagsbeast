package jags.functions;

import jags.nodes.JFunction;

public class Cos extends UnivariableFunction {

	public Cos(JFunction x) {
		super(x);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			values[i] = Math.cos(x.getArrayValue(i)); 
		}
	}

}
