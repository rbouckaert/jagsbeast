package jags.operators;

import beast.core.Function;
import jags.functions.BivariableFunction;

public class Pow extends BivariableFunction {

	public Pow(Function x, Function y) {
		super(x, y);
	}

	@Override
	protected double doTransform(double x, double y) {
		return Math.pow(x, y);
	}
}
