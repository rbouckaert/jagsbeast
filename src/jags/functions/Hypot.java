package jags.functions;

import beast.core.Function;

public class Hypot extends BivariableFunction {

	public Hypot(Function x, Function y) {
		super(x, y);
	}

	@Override
	protected  double doTransform(double x, double y) {		
		return Math.hypot(x, y);
	}

}
