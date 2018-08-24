package jags.operators;


import beast.core.Function;
import jags.functions.UnivariableFunction;

public class Complement extends UnivariableFunction {

	public Complement(Function x) {
		super(x);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			values[i] = ~(long)x.getArrayValue(i); 
		}
	}

}
