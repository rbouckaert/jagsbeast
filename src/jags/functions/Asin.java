package jags.functions;

import jags.nodes.JFunction;

public class Asin extends UnivariableFunction {

	public Asin(JFunction x) {
		super(x);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			values[i] = Math.asin(x.getArrayValue(i)); 
		}
	}

}
