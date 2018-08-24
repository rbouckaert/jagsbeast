package jags.operators;

import beast.core.Function;
import jags.functions.BivariableFunction;

public class Or extends BivariableFunction {

	public Or(Function x, Function y) {
		super(x, y);
	}

	@Override
	protected double doTransform(double x, double y) {
		return (x>0) || (y>0) ? 1 : 0;
	}
}
