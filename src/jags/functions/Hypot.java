package jags.functions;

import beast.core.Function;

public class Hypot extends BivariableFunction {

	public Hypot(Function x, Function y) {
		super(x, y);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			values[i] = Math.hypot(x.getArrayValue(i), y.getArrayValue(i)); 
		}
	}

}
