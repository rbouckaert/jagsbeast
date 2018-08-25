package jags.functions;

import jags.nodes.JFunction;

public class Acos extends UnivariableFunction {

	public Acos(JFunction x) {
		super(x);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			values[i] = Math.acos(x.getArrayValue(i)); 
		}
	}

}
