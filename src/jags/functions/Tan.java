package jags.functions;

import beast.core.Function;

public class Tan extends UnivariableFunction {

	public Tan(Function x) {
		super(x);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			values[i] = Math.tan(x.getArrayValue(i)); 
		}
	}

}
