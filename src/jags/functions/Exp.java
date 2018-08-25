package jags.functions;

import jags.nodes.JFunction;

public class Exp extends UnivariableFunction {

	public Exp(JFunction x) {
		super(x);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			values[i] = Math.exp(x.getArrayValue(i)); 
		}
	}

}
