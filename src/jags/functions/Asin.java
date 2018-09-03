package jags.functions;

import jags.nodes.JFunction;
import beast.core.Param;

public class Asin extends UnivariableFunction {

	public Asin(@Param(name="x", description="function or value argument") JFunction x) {
		super(x);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			values[i] = Math.asin(x.getArrayValue(i)); 
		}
	}

}
