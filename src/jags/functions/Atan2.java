package jags.functions;

import beast.core.Function;

public class Atan2 extends BivariableFunction {

	public Atan2(Function x, Function y) {
		super(x, y);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			values[i] = Math.atan2(x.getArrayValue(i), y.getArrayValue(i)); 
		}
	}

}
