package jags.operators;

import beast.core.Function;
import jags.functions.BivariableFunction;

public class Plus extends BivariableFunction {

	public Plus(Function x, Function y) {
		super(x, y);
	}

	@Override
	protected double doTransform(double x, double y) {
		return x + y;
	}
}
