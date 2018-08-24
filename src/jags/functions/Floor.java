package jags.functions;

import beast.core.Function;

public class Floor extends UnivariableFunction {

	public Floor(Function x) {
		super(x);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			values[i] = Math.floor(x.getArrayValue(i)); 
		}
	}

}
