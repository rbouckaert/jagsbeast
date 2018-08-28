package jags.operators;

import jags.nodes.JFunction;
import jags.functions.BivariableFunction;

public class Index extends BivariableFunction {

	public Index(JFunction x, JFunction y) {
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
