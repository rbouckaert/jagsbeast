package jags.functions;

import jags.nodes.JFunction;

public class Times extends BivariableFunction {

	public Times(JFunction x, JFunction y) {
		super(x, y);
	}

	@Override
	protected double doTransform(double x, double y) {
		return x * y;
	}
}
