package jags.functions;

import jags.nodes.JFunction;
import beast.core.Param;

import beast.core.Description;

@Description("Performs the Atanh function")
public class Atanh extends UnivariableFunction {
    public Atanh(){}


	public Atanh(@Param(name="x", description="function or value argument") JFunction x) {
		super(x);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			double val = x.getArrayValue(i);
			values[i] = (0.5 * Math.log((1 + val) / (1 - val)));
		}
	}

	@Override
	public String getJAGSAlias() {return "arctanh";}
}
