package jags.functions;

import beast.core.Function;

public class Expm1 extends UnivariableFunction {

	public Expm1(Function x) {
		super(x);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			values[i] = Math.expm1(x.getArrayValue(i)); 
		}
	}

}
