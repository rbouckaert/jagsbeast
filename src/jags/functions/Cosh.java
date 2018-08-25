package jags.functions;

import jags.nodes.JFunction;

public class Cosh extends UnivariableFunction {

	public Cosh(JFunction x) {
		super(x);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			values[i] = Math.cosh(x.getArrayValue(i)); 
		}
	}

}
