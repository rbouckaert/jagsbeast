package jags.functions;

import jags.nodes.JFunction;
import beast.core.Param;

import beast.core.Description;

@Description("Performs the Log function")
public class Log10 extends UnivariableFunction {
    public Log10(){}


	public Log10(@Param(name="x", description="function or value argument") JFunction x) {
		super(x);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			values[i] = Math.log10(x.getArrayValue(i)); 
		}
	}

}
