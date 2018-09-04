package jags.functions;

import jags.nodes.JFunction;
import beast.core.Param;

import beast.core.Description;

@Description("Performs the Hypot function")
public class Hypot extends BivariableFunction {

	public Hypot(@Param(name="x", description="first function or value argument") JFunction x, @Param(name="y", description="second function or value argument")JFunction y) {
		super(x, y);
	}

	@Override
	protected  double doTransform(double x, double y) {		
		return Math.hypot(x, y);
	}

}
