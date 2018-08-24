package jags.functions;

import beast.core.Function;

public class Log10 extends UnivariableFunction {

	public Log10(Function x) {
		super(x);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			values[i] = Math.log10(x.getArrayValue(i)); 
		}
	}

}
