package jags.operators;


import jags.nodes.JFunction;
import beast.core.Param;
import jags.functions.UnivariableFunction;

public class Complement extends UnivariableFunction {

	public Complement(@Param(name="x", description="value or function to take complement from") JFunction x) {
		super(x);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			values[i] = ~(long)x.getArrayValue(i); 
		}
	}

}
