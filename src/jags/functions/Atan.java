package jags.functions;

import jags.nodes.JFunction;
import beast.core.Param;

import beast.core.Description;

@Description("Performs the Atan function")
public class Atan extends UnivariableFunction {
    public Atan(){}


	public Atan(@Param(name="x", description="function or value argument") JFunction x) {
		super(x);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			values[i] = Math.atan(x.getArrayValue(i)); 
		}
	}

	@Override
	public String getJAGSAlias() {return "arctan";}
}
