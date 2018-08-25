package jags.functions;

import jags.nodes.JFunction;

public class Expm1 extends UnivariableFunction {

	public Expm1(JFunction x) {
		super(x);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			values[i] = Math.expm1(x.getArrayValue(i)); 
		}
	}

}
