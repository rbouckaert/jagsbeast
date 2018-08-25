package jags.operators;


import jags.nodes.JFunction;
import jags.functions.UnivariableFunction;

public class Not extends UnivariableFunction {

	public Not(JFunction x) {
		super(x);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			values[i] = (x.getArrayValue(i) > 0) ? 0 : 1; 
		}
	}

}
