package jags.functions;

import jags.nodes.JFunction;
import beast.core.Param;

import beast.core.Description;

@Description("Performs the Sinh function")
public class Sinh extends UnivariableFunction {
    public Sinh(){}


	public Sinh(@Param(name="x", description="function or value argument") JFunction x) {
		super(x);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			values[i] = Math.sinh(x.getArrayValue(i)); 
		}
	}

}
