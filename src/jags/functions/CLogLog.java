package jags.functions;

import jags.nodes.JFunction;
import beast.core.Param;

import beast.core.Description;

@Description("Performs the CLogLog function")
public class CLogLog extends UnivariableFunction {
    public CLogLog(){}


	public CLogLog(@Param(name="x", description="function or value argument") JFunction x) {
		super(x);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			values[i] = Math.log(-Math.log(1 - x.getArrayValue(i))); 
		}
	}

}
