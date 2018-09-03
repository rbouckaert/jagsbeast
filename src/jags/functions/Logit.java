package jags.functions;

import jags.nodes.JFunction;
import beast.core.Param;

public class Logit extends UnivariableFunction {

	public Logit(@Param(name="x", description="function or value argument") JFunction x) {
		super(x);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			double arg = x.getArrayValue(i);
			values[i] = Math.log(arg) - Math.log(1 - arg);
		}
	}

}
