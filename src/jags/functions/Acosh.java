package jags.functions;

import jags.nodes.JFunction;
import beast.core.Param;

import beast.core.Description;

@Description("Performs the Acosh function")
public class Acosh extends UnivariableFunction {
    public Acosh(){}


	public Acosh(@Param(name="x", description="function or value argument") JFunction x) {
		super(x);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			double val = x.getArrayValue(i);
			values[i] = (Math.log(val + Math.sqrt(val + 1) * Math.sqrt(val - 1)));
		}
	}

	@Override
	public String getJAGSAlias() {return "arccosh";}
}
