package jags.functions;

import jags.nodes.JFunction;
import beast.core.Param;

public class Cos extends UnivariableFunction {

	public Cos(@Param(name="x", description="function or value argument") JFunction x) {
		super(x);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			values[i] = Math.cos(x.getArrayValue(i)); 
		}
	}

}
