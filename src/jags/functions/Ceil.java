package jags.functions;

import jags.nodes.JFunction;

public class Ceil extends UnivariableFunction {

	public Ceil(JFunction x) {
		super(x);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			values[i] = Math.ceil(x.getArrayValue(i)); 
		}
	}

}
