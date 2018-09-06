package jags.functions;

import jags.nodes.JFunction;
import beast.core.Param;

import beast.core.Description;

@Description("Performs the Log function")
public class Log1p extends UnivariableFunction {
    public Log1p(){}


	public Log1p(@Param(name="x", description="function or value argument") JFunction x) {
		super(x);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			values[i] = Math.log1p(x.getArrayValue(i)); 
		}
	}

}
