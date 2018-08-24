package jags.operators;

import beast.core.Function;
import jags.functions.BivariableFunction;

public class Pow extends BivariableFunction {

	public Pow(Function x, Function y) {
		super(x, y);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			values[i] = Math.pow(x.getArrayValue(i), y.getArrayValue(i)); 
		}
	}

}
