package jags.functions;

import jags.nodes.JFunction;
import beast.core.Param;

public class Expm1 extends UnivariableFunction {

	public Expm1(@Param(name="x", description="function or value argument") JFunction x) {
		super(x);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			values[i] = Math.expm1(x.getArrayValue(i)); 
		}
	}

}
