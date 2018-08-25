package jags.functions;

import jags.nodes.JFunction;

public class Max extends BivariableFunction {

	public Max(JFunction x, JFunction y) {
		super(x, y);
	}

	@Override
	protected  double doTransform(double x, double y) {
		return Math.max(x, y);
	}
}
