package jags.functions;

import beast.core.Function;

public class Acos extends UnivariableFunction {

	public Acos(Function x) {
		super(x);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			values[i] = Math.acos(x.getArrayValue(i)); 
		}
	}

}
