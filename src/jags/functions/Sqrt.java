package jags.functions;

import beast.core.Function;

public class Sqrt extends UnivariableFunction {

	public Sqrt(Function x) {
		super(x);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			values[i] = Math.sqrt(x.getArrayValue(i)); 
		}
	}

}
