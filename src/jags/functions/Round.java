package jags.functions;

import jags.nodes.JFunction;

public class Round extends UnivariableFunction {

	public Round(JFunction x) {
		super(x);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			values[i] = Math.round(x.getArrayValue(i)); 
		}
	}

}
