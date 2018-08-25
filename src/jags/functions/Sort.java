package jags.functions;

import java.util.Arrays;

import jags.nodes.JFunction;

public class Sort extends UnivariableFunction {

	public Sort(JFunction x) {
		super(x);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			values[i] = x.getArrayValue(i); 
		}
		Arrays.sort(values);
	}

}
