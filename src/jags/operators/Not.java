package jags.operators;


import beast.core.Function;
import jags.functions.UnivariableFunction;

public class Not extends UnivariableFunction {

	public Not(Function x) {
		super(x);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			values[i] = (x.getArrayValue(i) > 0) ? 0 : 1; 
		}
	}

}
