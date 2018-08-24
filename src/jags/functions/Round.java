package jags.functions;

import beast.core.Function;

public class Round extends UnivariableFunction {

	public Round(Function x) {
		super(x);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			values[i] = Math.round(x.getArrayValue(i)); 
		}
	}

}
