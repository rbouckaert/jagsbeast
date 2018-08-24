package jags.functions;

import beast.core.Function;

public class Min extends BivariableFunction {

	public Min(Function x, Function y) {
		super(x, y);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			values[i] = Math.min(x.getArrayValue(i), y.getArrayValue(i)); 
		}
	}

}
