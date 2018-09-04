package jags.operators;

import jags.functions.BivariableFunction;
import jags.nodes.JFunction;
import beast.core.Param;

import beast.core.Description;

@Description("Performs the Times function")
public class Times extends BivariableFunction {

	public Times(@Param(name="x", description="first function or value argument") JFunction x, @Param(name="y", description="second function or value argument")JFunction y) {
		super(x, y);
	}

	@Override
	protected double doTransform(double x, double y) {
		return x * y;
	}
}
