package jags.operators;

import beast.core.Function;
import jags.functions.BivariableFunction;

public class And extends BivariableFunction {

	public And(Function x, Function y) {
		super(x, y);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			values[i] = (x.getArrayValue(i)>0) && (y.getArrayValue(i)>0) ? 1 : 0; 
		}
	}

}
