package jags.functions;

import jags.nodes.JFunction;

public class Atan2 extends BivariableFunction {

	public Atan2(JFunction x, JFunction y) {
		super(x, y);
	}

	@Override
	protected double doTransform(double x, double y) {
		return Math.atan2(x, y);
	}
	
}
