package jags.operators;

import jags.nodes.JFunction;
import jags.functions.BivariableFunction;

public class And extends BivariableFunction {

	public And(JFunction x, JFunction y) {
		super(x, y);
	}

	
	@Override
	protected double doTransform(double x, double y) {
		return  (x>0) && (y>0) ? 1 : 0;
	}

}
