package jags.functions;

import beast.core.Function;

public class Min extends BivariableFunction {

	public Min(Function x, Function y) {
		super(x, y);
	}
	
	@Override
	protected double doTransform(double x, double y) {
		return Math.min(x, y);
	}

}
