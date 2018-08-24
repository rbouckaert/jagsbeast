package jags.operators;

import beast.core.Function;
import jags.functions.BivariableFunction;

public class RightShift extends BivariableFunction {

	public RightShift(Function x, Function y) {
		super(x, y);
	}

	@Override
	protected double doTransform(double x, double y) {
		return ((int)x >> (int)y);
	}
}
