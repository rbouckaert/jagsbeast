package jags.functions;

import beast.core.Function;

public class Cosh extends UnivariableFunction {

	public Cosh(Function x) {
		super(x);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			values[i] = Math.cosh(x.getArrayValue(i)); 
		}
	}

}
