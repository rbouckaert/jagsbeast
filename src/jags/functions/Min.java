package jags.functions;

import jags.nodes.JFunction;

public class Min extends BivariableFunction {

	public Min(JFunction x, JFunction y) {
		super(x, y);
	}
	
	@Override
	protected double doTransform(double x, double y) {
		return Math.min(x, y);
	}

}
