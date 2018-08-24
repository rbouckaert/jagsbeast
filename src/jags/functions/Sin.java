package jags.functions;

import beast.core.Function;

public class Sin extends UnivariableFunction {

	public Sin(Function x) {
		super(x);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			values[i] = Math.sin(x.getArrayValue(i)); 
		}
	}

}
