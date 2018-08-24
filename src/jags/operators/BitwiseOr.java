package jags.operators;

import beast.core.Function;
import jags.functions.BivariableFunction;

public class BitwiseOr extends BivariableFunction {

	public BitwiseOr(Function x, Function y) {
		super(x, y);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			values[i] = ((int)x.getArrayValue(i) | (int)y.getArrayValue(i));  
		}
	}

}
