package jags.functions;

import jags.nodes.JFunction;

public class Hypot extends BivariableFunction {

	public Hypot(JFunction x, JFunction y) {
		super(x, y);
	}

	@Override
	protected  double doTransform(double x, double y) {		
		return Math.hypot(x, y);
	}

}
