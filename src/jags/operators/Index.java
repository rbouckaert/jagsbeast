package jags.operators;

import beast.core.Function;
import jags.functions.BivariableFunction;

public class Index extends BivariableFunction {

	public Index(Function x, Function y) {
		super(x, y);
		values = new double[1];
		storedvalues = new double[1];
	}

	@Override
	protected void doTransform() {
		values[0] = x.getArrayValue((int) y.getArrayValue());
	}
	
	@Override
	protected void dimensionCheck() {
	}

}
