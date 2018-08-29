package jags.operators;

import jags.nodes.JFunction;
import beast.core.Param;
import jags.functions.BivariableFunction;

public class Index extends BivariableFunction {

	public Index(@Param(name="x", description="variable to be indexed") JFunction x, 
			@Param(name="index", description="possibly multivalued index used to identify element") JFunction y) {
		super(x, y);
		values = new double[1];
		storedvalues = new double[1];
	}

	@Override
	protected void doTransform() {
		values[0] = x.getArrayValue((int) y.getArrayValue() - 1);
	}
	
	
	
	@Override
	protected int dimensionCheck() {
		mode = Mode.mode11;
		return 1;
	}

	@Override
	protected double doTransform(double x, double y) {
		throw new RuntimeException("Do not call doTransform(x,y), call doTransform() instead");
	}

}
