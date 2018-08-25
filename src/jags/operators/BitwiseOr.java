package jags.operators;

import jags.nodes.JFunction;
import jags.functions.BivariableFunction;

public class BitwiseOr extends BivariableFunction {

	public BitwiseOr(JFunction x, JFunction y) {
		super(x, y);
	}

	@Override
	protected double doTransform(double x, double y) {
		return ((int)x | (int)y);
	}
}
