package jags.operators;


import jags.nodes.JFunction;
import jags.functions.UnivariableFunction;

public class Length extends UnivariableFunction {

	public Length(JFunction x) {
		super(x);
		values = new double[1];
		storedvalues = new double[1];
	}

	@Override
	protected void doTransform() {
		values[0] = x.getDimension(); 		
	}

}
