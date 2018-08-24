package jags.operators;

import beast.core.Function;
import jags.functions.BivariableFunction;

public class Eq extends BivariableFunction {

	public Eq(Function x, Function y) {
		super(x, y);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			values[i] = (x.getArrayValue(i) == y.getArrayValue(i)) ? 1 : 0; 
		}
	}

}
