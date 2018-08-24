package jags.functions;

import beast.core.Function;

public class Atan2 extends BivariableFunction {

	public Atan2(Function x, Function y) {
		super(x, y);
	}

	@Override
	protected double doTransform(double x, double y) {
		return Math.atan2(x, y);
	}
	
}
