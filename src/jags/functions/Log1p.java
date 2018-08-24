package jags.functions;

import beast.core.Function;

public class Log1p extends UnivariableFunction {

	public Log1p(Function x) {
		super(x);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			values[i] = Math.log1p(x.getArrayValue(i)); 
		}
	}

}
