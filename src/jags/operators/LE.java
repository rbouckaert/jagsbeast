package jags.operators;

import jags.nodes.JFunction;
import jags.functions.BivariableFunction;

public class LE extends BivariableFunction {

	public LE(JFunction x, JFunction y) {
		super(x, y);
	}

	@Override
	protected double doTransform(double x, double y) {
		return (x <= y) ? 1 : 0;
	}
}
