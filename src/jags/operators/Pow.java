package jags.operators;

import jags.nodes.JFunction;
import jags.functions.BivariableFunction;

public class Pow extends BivariableFunction {

	public Pow(JFunction x, JFunction y) {
		super(x, y);
	}

	@Override
	protected double doTransform(double x, double y) {
		return Math.pow(x, y);
	}
}
