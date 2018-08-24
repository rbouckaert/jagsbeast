package jags.operators;

import beast.core.Function;
import jags.functions.BivariableFunction;

public class Div extends BivariableFunction {

	public Div(Function x, Function y) {
		super(x, y);
	}

	@Override
	protected double doTransform(double x, double y) {
		return x / y;
	}
}
