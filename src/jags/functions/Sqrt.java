package jags.functions;

import jags.nodes.JFunction;
import beast.core.Param;

import beast.core.Description;

@Description("Performs the Sqrt function")
public class Sqrt extends UnivariableFunction {
    public Sqrt(){}


	public Sqrt(@Param(name="x", description="function or value argument") JFunction x) {
		super(x);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			values[i] = Math.sqrt(x.getArrayValue(i)); 
		}
	}

}
