package jags.operators;

import beast.core.Function;
import jags.functions.BivariableFunction;

public class Modulo extends BivariableFunction {

	public Modulo(Function x, Function y) {
		super(x, y);
	}

	@Override
	protected double doTransform(double x, double y) {
		return x % y;
	}
}
