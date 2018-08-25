package jags.functions;

import jags.nodes.JFunction;

public class Sqrt extends UnivariableFunction {

	public Sqrt(JFunction x) {
		super(x);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			values[i] = Math.sqrt(x.getArrayValue(i)); 
		}
	}

}
