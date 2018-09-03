package jags.functions;

import org.apache.commons.math.special.Gamma;

import jags.nodes.JFunction;
import beast.core.Param;

public class LogGamma extends UnivariableFunction {

	public LogGamma(@Param(name="x", description="function or value argument") JFunction x) {
		super(x);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			double val = x.getArrayValue(i);
			values[i] = Gamma.logGamma(val);					
		}
	}

}
