package jags.functions;

import beast.core.Param;
import jags.nodes.JFunction;

import beast.core.Description;

@Description("Performs the Abs function")
public class Abs extends UnivariableFunction {
    public Abs(){}


	public Abs(@Param(name="x", description="function or value argument") JFunction x) {
		super(x);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			values[i] = Math.abs(x.getArrayValue(i)); 
		}
	}

}
