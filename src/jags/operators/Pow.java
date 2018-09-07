package jags.operators;

import jags.nodes.JFunction;
import beast.core.Param;
import jags.functions.BivariableFunction;

import beast.core.Description;

@Description("Performs the Pow operation")
public class Pow extends BivariableFunction {
	public Pow() {}

	public Pow(@Param(name="x", description="first function or value argument") JFunction x, @Param(name="y", description="second function or value argument")JFunction y) {
		super(x, y);
	}

	@Override
	protected double doTransform(double x, double y) {
		return Math.pow(x, y);
	}
}
