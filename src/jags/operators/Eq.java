package jags.operators;

import jags.nodes.JFunction;
import jags.functions.BivariableFunction;

public class Eq extends BivariableFunction {

	public Eq(JFunction x, JFunction y) {
		super(x, y);
	}

	@Override
	protected double doTransform(double x, double y) {
		return (x == y) ? 1 : 0;
	}
}
