package jags.functions;

import beast.core.Function;

public class Atan extends UnivariableFunction {

	public Atan(Function x) {
		super(x);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			values[i] = Math.atan(x.getArrayValue(i)); 
		}
	}

}
