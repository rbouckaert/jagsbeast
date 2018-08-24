package jags.functions;

import beast.core.Function;

public class Asin extends UnivariableFunction {

	public Asin(Function x) {
		super(x);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			values[i] = Math.asin(x.getArrayValue(i)); 
		}
	}

}
