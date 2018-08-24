package jags.functions;

import beast.core.Function;

public class Abs extends UnivariableFunction {

	public Abs(Function x) {
		super(x);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			values[i] = Math.abs(x.getArrayValue(i)); 
		}
	}

}
