package jags.functions;


import jags.nodes.JFunction;
import beast.core.Param;

public class Atan2 extends BivariableFunction {

	public Atan2(@Param(name="x", description="first function or value argument") JFunction x, @Param(name="y", description="second function or value argument")JFunction y) {
		super(x, y);
	}

	@Override
	protected double doTransform(double x, double y) {
		return Math.atan2(x, y);
	}
	
}
