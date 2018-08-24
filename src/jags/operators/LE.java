package jags.operators;

import beast.core.Function;
import jags.functions.BivariableFunction;

public class LE extends BivariableFunction {

	public LE(Function x, Function y) {
		super(x, y);
	}

	@Override
	protected double doTransform(double x, double y) {
		return (x <= y) ? 1 : 0;
	}
}
