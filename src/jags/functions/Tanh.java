package jags.functions;

import beast.core.Function;

public class Tanh extends UnivariableFunction {

	public Tanh(Function x) {
		super(x);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			values[i] = Math.tanh(x.getArrayValue(i)); 
		}
	}

}
