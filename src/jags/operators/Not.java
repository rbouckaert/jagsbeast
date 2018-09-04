package jags.operators;


import jags.nodes.JFunction;
import beast.core.Param;
import jags.functions.UnivariableFunction;

import beast.core.Description;

@Description("Performs the Not operation")
public class Not extends UnivariableFunction {

	public Not(@Param(name="x", description="value or function to apply not() to") JFunction x) {
		super(x);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			values[i] = (x.getArrayValue(i) > 0) ? 0 : 1; 
		}
	}

}
