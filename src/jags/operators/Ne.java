package jags.operators;

import jags.nodes.JFunction;
import beast.core.Param;
import jags.functions.BivariableFunction;

import beast.core.Description;

@Description("Performs the Ne operation")
public class Ne extends BivariableFunction {

	public Ne(@Param(name="x", description="first function or value argument") JFunction x, @Param(name="y", description="second function or value argument")JFunction y) {
		super(x, y);
	}

	@Override
	protected double doTransform(double x, double y) {
		return (x != y) ? 1 : 0;
	}
}
