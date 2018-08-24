package jags.functions;

import beast.core.Function;

public class Log extends UnivariableFunction {

	public Log(Function x) {
		super(x);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			values[i] = Math.log(x.getArrayValue(i)); 
		}
	}

}
