package jags.functions;

import beast.core.Function;

public class Cbrt extends UnivariableFunction {

	public Cbrt(Function x) {
		super(x);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			values[i] = Math.cbrt(x.getArrayValue(i)); 
		}
	}

}
