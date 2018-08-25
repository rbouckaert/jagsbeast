package jags.functions;

import jags.nodes.JFunction;

public class Log extends UnivariableFunction {

	public Log(JFunction x) {
		super(x);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			values[i] = Math.log(x.getArrayValue(i)); 
		}
	}

}
