package jags.functions;

import beast.core.Function;

public class Max extends BivariableFunction {

	public Max(Function x, Function y) {
		super(x, y);
	}

	@Override
	protected  double doTransform(double x, double y) {
		return Math.max(x, y);
	}
}
