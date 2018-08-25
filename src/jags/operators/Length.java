package jags.operators;


import beast.core.Function;
import jags.functions.UnivariableFunction;

public class Length extends UnivariableFunction {

	public Length(Function x) {
		super(x);
		values = new double[1];
		storedvalues = new double[1];
	}

	@Override
	protected void doTransform() {
		values[0] = x.getDimension(); 		
	}

}
