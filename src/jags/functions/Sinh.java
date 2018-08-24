package jags.functions;

import beast.core.Function;

public class Sinh extends UnivariableFunction {

	public Sinh(Function x) {
		super(x);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			values[i] = Math.sinh(x.getArrayValue(i)); 
		}
	}

}
