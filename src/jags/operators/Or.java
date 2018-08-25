package jags.operators;

import jags.nodes.JFunction;
import jags.functions.BivariableFunction;

public class Or extends BivariableFunction {

	public Or(JFunction x, JFunction y) {
		super(x, y);
	}

	@Override
	protected double doTransform(double x, double y) {
		return (x>0) || (y>0) ? 1 : 0;
	}
}
