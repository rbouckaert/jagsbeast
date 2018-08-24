package jags.functions;

import beast.core.Function;

public class Exp extends UnivariableFunction {

	public Exp(Function x) {
		super(x);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			values[i] = Math.exp(x.getArrayValue(i)); 
		}
	}

}
