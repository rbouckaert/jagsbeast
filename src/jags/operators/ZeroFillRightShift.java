package jags.operators;

import beast.core.Function;
import jags.functions.BivariableFunction;

public class ZeroFillRightShift extends BivariableFunction {

	public ZeroFillRightShift(Function x, Function y) {
		super(x, y);
	}

	@Override
	protected double doTransform(double x, double y) {
		return ((int)x >>> (int)y);
	}
}
