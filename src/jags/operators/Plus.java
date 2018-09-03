package jags.operators;

import jags.nodes.JFunction;
import beast.core.Param;
import jags.functions.BivariableFunction;

public class Plus extends BivariableFunction {

	public Plus(@Param(name="x", description="first function or value argument") JFunction x, @Param(name="y", description="second function or value argument")JFunction y) {
		super(x, y);
	}

	@Override
	protected double doTransform(double x, double y) {
		return x + y;
	}
}
