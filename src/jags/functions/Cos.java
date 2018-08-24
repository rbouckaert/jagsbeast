package jags.functions;

import beast.core.Function;

public class Cos extends UnivariableFunction {

	public Cos(Function x) {
		super(x);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			values[i] = Math.cos(x.getArrayValue(i)); 
		}
	}

}
