package jags.operators;

import beast.core.Function;
import jags.functions.BivariableFunction;

public class Times extends BivariableFunction {

	public Times(Function x, Function y) {
		super(x, y);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			values[i] = x.getArrayValue(i) * y.getArrayValue(i); 
		}
	}

}
