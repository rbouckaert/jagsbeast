package jags.functions;

import jags.nodes.JFunction;

public class CLogLog extends UnivariableFunction {

	public CLogLog(JFunction x) {
		super(x);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			values[i] = Math.log(-Math.log(1 - x.getArrayValue(i))); 
		}
	}

}
