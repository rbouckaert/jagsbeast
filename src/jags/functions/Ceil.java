package jags.functions;

import beast.core.Function;

public class Ceil extends UnivariableFunction {

	public Ceil(Function x) {
		super(x);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			values[i] = Math.ceil(x.getArrayValue(i)); 
		}
	}

}
