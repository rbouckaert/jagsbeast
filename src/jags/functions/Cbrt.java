package jags.functions;

import jags.nodes.JFunction;

public class Cbrt extends UnivariableFunction {

	public Cbrt(JFunction x) {
		super(x);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			values[i] = Math.cbrt(x.getArrayValue(i)); 
		}
	}

}
