package jags.functions;

import beast.core.Function;

public class Signum extends UnivariableFunction {

	public Signum(Function x) {
		super(x);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			values[i] = Math.signum(x.getArrayValue(i)); 
		}
	}

}
