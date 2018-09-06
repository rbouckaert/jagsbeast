package jags.functions;

import jags.nodes.JFunction;
import beast.core.Param;

import beast.core.Description;

@Description("Performs the Cosh function")
public class Cosh extends UnivariableFunction {
    public Cosh(){}


	public Cosh(@Param(name="x", description="function or value argument") JFunction x) {
		super(x);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			values[i] = Math.cosh(x.getArrayValue(i)); 
		}
	}

}
