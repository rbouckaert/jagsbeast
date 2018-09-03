package jags.functions;

import jags.nodes.JFunction;
import beast.core.Param;

public class Tan extends UnivariableFunction {

	public Tan(@Param(name="x", description="function or value argument") JFunction x) {
		super(x);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			values[i] = Math.tan(x.getArrayValue(i)); 
		}
	}

}
