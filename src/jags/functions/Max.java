package jags.functions;

import beast.core.Function;

public class Max extends BivariableFunction {

	public Max(Function x, Function y) {
		super(x, y);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			values[i] = Math.max(x.getArrayValue(i), y.getArrayValue(i)); 
		}
	}

}
