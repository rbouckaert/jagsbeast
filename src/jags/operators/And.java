package jags.operators;

import jags.nodes.JFunction;
import beast.core.Param;
import jags.functions.BivariableFunction;

public class And extends BivariableFunction {

	public And(@Param(name="x", description="first function or value argument") JFunction x, @Param(name="y", description="second function or value argument")JFunction y) {
		super(x, y);
	}

	
	@Override
	protected double doTransform(double x, double y) {
		return  (x>0) && (y>0) ? 1 : 0;
	}

}
